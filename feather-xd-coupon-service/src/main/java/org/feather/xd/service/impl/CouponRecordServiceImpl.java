package org.feather.xd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.config.RabbitMQConfig;
import org.feather.xd.constant.CommonConstant;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.enums.CouponStateEnum;
import org.feather.xd.enums.ProductOrderStateEnum;
import org.feather.xd.enums.StockTaskStateEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.feign.ProductOrderFeignService;
import org.feather.xd.interceptor.LoginInterceptor;
import org.feather.xd.mapper.CouponRecordMapper;
import org.feather.xd.model.CouponRecordDO;
import org.feather.xd.model.CouponRecordMessage;
import org.feather.xd.model.CouponTaskDO;
import org.feather.xd.model.LoginUser;
import org.feather.xd.query.CouponRecordQuery;
import org.feather.xd.request.LockCouponRequest;
import org.feather.xd.service.ICouponRecordService;
import org.feather.xd.service.ICouponTaskService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.CouponRecordVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecordDO> implements ICouponRecordService {

    private final ICouponTaskService couponTaskService;

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQConfig rabbitMQConfig;

    private final ProductOrderFeignService productOrderFeignService;
    @Override
    public Page<CouponRecordVO> pageCouponRecord(CouponRecordQuery query) {
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        LambdaQueryWrapper<CouponRecordDO> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponRecordDO::getUserId,loginUser.getId());
        queryWrapper.orderByDesc(CouponRecordDO::getCreateTime);
        Page<CouponRecordDO> page=new Page<>(query.getPageNo(),query.getPageSize());
        Page<CouponRecordDO> couponRecordPage = this.getBaseMapper().selectPage(page, queryWrapper);
        // 构建 Page<CouponVO> 对象
        Page<CouponRecordVO> CouponRecordVOPage = new Page<>(query.getPageNo(), query.getPageSize(), couponRecordPage.getTotal());
        CouponRecordVOPage.setRecords(BeanUtil.copyToList(couponRecordPage.getRecords(), CouponRecordVO.class));
        return CouponRecordVOPage;
    }

    @Override
    public CouponRecordVO findById(long recordId) {
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        CouponRecordDO couponRecordDO = Optional.ofNullable(
                this.getOne(
                        new LambdaQueryWrapper<CouponRecordDO>()
                .eq(CouponRecordDO::getCouponId,recordId)
                .eq(CouponRecordDO::getUserId,loginUser.getId()))).orElseThrow(()->new BizException(BizCodeEnum.COUPON_NO_EXITS));
        return BeanUtil.copyProperties(couponRecordDO, CouponRecordVO.class);
    }

    @Override
    public void lockCouponRecords(LockCouponRequest request) {
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        String orderOutTradeNo = request.getOrderOutTradeNo();
        List<Long> lockCouponRecordIds = request.getLockCouponRecordIds();
        //批量更新优惠券使用记录
        LambdaUpdateWrapper<CouponRecordDO> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(CouponRecordDO::getUserId,loginUser.getId());
        updateWrapper.eq(CouponRecordDO::getUseState, CouponStateEnum.NEW.name());
        updateWrapper.set(CouponRecordDO::getUseState, CouponStateEnum.USED.name());
        updateWrapper.in(CouponRecordDO::getCouponId,lockCouponRecordIds);
        boolean update = this.update(updateWrapper);
        log.info("优惠券记录锁定:[{}],记录:[{}]",update,lockCouponRecordIds);
        List<CouponTaskDO> couponTaskDOList =  lockCouponRecordIds.stream().map(obj->{
            CouponTaskDO couponTaskDO = new CouponTaskDO();
            couponTaskDO.setOutTradeNo(orderOutTradeNo);
            couponTaskDO.setCouponRecordId(obj);
            couponTaskDO.setLockState(StockTaskStateEnum.LOCK.name());
            return couponTaskDO;
        }).collect(Collectors.toList());
        boolean saveBatch = couponTaskService.saveBatch(couponTaskDOList);
        log.info("新增优惠券记录task :[{}]",saveBatch);
        if (!update||!saveBatch){
                throw new BizException(BizCodeEnum.COUPON_RECORD_LOCK_FAIL);
        }
        //发送延迟消息
        for(CouponTaskDO couponTaskDO : couponTaskDOList){
            CouponRecordMessage couponRecordMessage = new CouponRecordMessage();
            couponRecordMessage.setOutTradeNo(orderOutTradeNo);
            couponRecordMessage.setTaskId(couponTaskDO.getId());

            rabbitTemplate.convertAndSend(rabbitMQConfig.getEventExchange(),rabbitMQConfig.getCouponReleaseDelayRoutingKey(),couponRecordMessage);
            log.info("优惠券锁定消息发送成功:[{}]",couponRecordMessage);
        }


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean releaseCouponRecord(CouponRecordMessage recordMessage) {
        CouponTaskDO couponTaskDO = couponTaskService.getById(recordMessage.getTaskId());
        if (Objects.isNull(couponTaskDO)){
            log.warn("工作单不存在,消息:[{}]",recordMessage);
            return true;
        }
        //lock 状态才处理
        if (couponTaskDO.getLockState().equalsIgnoreCase(StockTaskStateEnum.LOCK.name())){
            log.warn("工作单状态错误,消息:[{}]",recordMessage);
          // 查询订单状态
            JsonResult<String> jsonResult = productOrderFeignService.queryProductOrderState(recordMessage.getOutTradeNo());
            if (jsonResult.getCode().equals(CommonConstant.SUCCESS_CODE)){
                //正常响应 判断订单状态
                String state = jsonResult.getData();
                if (ProductOrderStateEnum.NEW.name().equalsIgnoreCase(state)){
                    //订单是NEw状态，则返回消息队列，重新创建
                    log.warn("订单状态是NEW,返回给消息队列，重新投递:{}",recordMessage);
                    return false;
                }
                //已支付
                if (ProductOrderStateEnum.PAY.name().equalsIgnoreCase(state)){
                    //订单是PAYED状态，则更新优惠券状态为已使用
                    couponTaskDO.setLockState(StockTaskStateEnum.FINISH.name());
                    couponTaskService.update(
                            new LambdaQueryWrapper<CouponTaskDO>().eq(CouponTaskDO::getId,recordMessage.getTaskId())
                    );
                    log.info("订单已经支付，修改库存锁定工作单FINISH状态:{}",recordMessage);
                    return true;
                }

            }
            //订单不存在，或者订单被取消，确认消息,修改task状态为CANCEL,恢复优惠券使用记录为NEW
            log.warn("订单不存在，或者订单被取消，确认消息,修改task状态为CANCEL,恢复优惠券使用记录为NEW,message:{}",recordMessage);
                couponTaskDO.setLockState(StockTaskStateEnum.CANCEL.name());

            couponTaskService.update(new LambdaQueryWrapper<CouponTaskDO>().eq(CouponTaskDO::getId,recordMessage.getTaskId()));
            //恢复优惠券记录是NEW状态
            LambdaUpdateWrapper<CouponRecordDO> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.eq(CouponRecordDO::getId,recordMessage.getMessageId());
            updateWrapper.set(CouponRecordDO::getUseState,CouponStateEnum.NEW.name());
            this.update(updateWrapper);
            return true;
        }else {
            log.warn("工作单状态不是LOCK,state={},消息体={}",couponTaskDO.getLockState(),recordMessage);
            return  true;
        }

    }
}
