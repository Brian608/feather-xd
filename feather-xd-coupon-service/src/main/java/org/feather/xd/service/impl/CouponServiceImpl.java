package org.feather.xd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.feather.xd.enums.CouponCategoryEnum;
import org.feather.xd.enums.CouponPublishEnum;
import org.feather.xd.model.CouponDO;
import org.feather.xd.mapper.CouponMapper;
import org.feather.xd.query.CouponQuery;
import org.feather.xd.service.ICouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.vo.CouponVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements ICouponService {

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
}
