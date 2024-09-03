package org.feather.xd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.interceptor.LoginInterceptor;
import org.feather.xd.model.CouponRecordDO;
import org.feather.xd.mapper.CouponRecordMapper;
import org.feather.xd.model.LoginUser;
import org.feather.xd.query.CouponRecordQuery;
import org.feather.xd.service.ICouponRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.vo.CouponRecordVO;
import org.feather.xd.vo.CouponVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
@Service
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecordDO> implements ICouponRecordService {

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
}
