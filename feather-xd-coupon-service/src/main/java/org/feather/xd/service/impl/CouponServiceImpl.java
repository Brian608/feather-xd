package org.feather.xd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.enums.CouponCategoryEnum;
import org.feather.xd.enums.CouponPublishEnum;
import org.feather.xd.enums.CouponStateEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.interceptor.LoginInterceptor;
import org.feather.xd.model.CouponDO;
import org.feather.xd.mapper.CouponMapper;
import org.feather.xd.model.CouponRecordDO;
import org.feather.xd.model.LoginUser;
import org.feather.xd.query.CouponQuery;
import org.feather.xd.service.ICouponRecordService;
import org.feather.xd.service.ICouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.util.CommonUtil;
import org.feather.xd.vo.CouponVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
@Slf4j
@AllArgsConstructor
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements ICouponService {

    private final ICouponRecordService couponRecordService;

    private  final CouponMapper couponMapper;
    @Override
    public Page<CouponVO> pageCoupon(CouponQuery query) {
        LambdaQueryWrapper<CouponDO> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CouponDO::getPublish, CouponPublishEnum.PUBLISH);
        queryWrapper.eq(CouponDO::getCategory, CouponCategoryEnum.PROMOTION);
        queryWrapper.like(StringUtils.isNotBlank(query.getCouponTitle()),CouponDO::getCouponTitle,query.getCouponTitle());
        queryWrapper.orderByDesc(CouponDO::getCreateTime);
        Page<CouponDO> page=new Page<>(query.getPageNo(),query.getPageSize());
        Page<CouponDO> couponPage=  this.page(page,queryWrapper);

        // 构建 Page<CouponVO> 对象
        Page<CouponVO> couponVOPage = new Page<>(query.getPageNo(), query.getPageSize(), couponPage.getTotal());
        couponVOPage.setRecords(BeanUtil.copyToList(couponPage.getRecords(), CouponVO.class));
        return couponVOPage;
    }

    @Transactional(rollbackFor=Exception.class,propagation= Propagation.REQUIRED)
    @Override
    public void getCoupon(long couponId) {
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        CouponDO couponDO = Optional.ofNullable(this.getById(couponId)).orElseThrow(() -> new BizException(BizCodeEnum.COUPON_NO_EXITS));
        //检查优惠券是否可以领取
        this.checkCoupon(loginUser.getId(),couponDO);

        //假设每个用户每次只能领取一张优惠券
         int rows=  couponMapper.reduceStock(couponId,1);

        if (rows >0) {
            //库存扣减成功才保存记录
            //构建领劵记录
            CouponRecordDO couponRecordDO = new CouponRecordDO();
            //忽悠优惠券id
            BeanUtils.copyProperties(couponDO, couponRecordDO,"id");
            couponRecordDO.setUseState(CouponStateEnum.NEW.name());
            couponRecordDO.setUserId(loginUser.getId());
            couponRecordDO.setUserName(loginUser.getName());
            couponRecordDO.setCouponId(couponId);
            couponRecordService.save(couponRecordDO);

        } else {
            log.warn("领取优惠券失败:[{}],用户:[{}]", couponDO, loginUser);
            throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
        }

    }

    private void checkCoupon(Long userId, CouponDO couponDO) {
        //库存是否足够
        if (couponDO.getStock() <= 0) {
            throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
        }
        //判断是否是否发布状态
        if(!couponDO.getPublish().equals(CouponPublishEnum.PUBLISH.name())){
            throw new BizException(BizCodeEnum.COUPON_GET_FAIL);
        }
        //是否在领取时间范围
        long time = CommonUtil.getCurrentTimestamp();
        long start = couponDO.getStartTime().getTime();
        long end = couponDO.getEndTime().getTime();
        if(time<start || time>end){
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_TIME);
        }

        //用户是否超过限制
        int recordNum =  couponRecordService.count(new LambdaQueryWrapper<CouponRecordDO>()
                .eq(CouponRecordDO::getCouponId,couponDO.getId())
                .eq(CouponRecordDO::getUserId,userId));

        if(recordNum >= couponDO.getUserLimit()){
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_LIMIT);
        }

    }
}
