package org.feather.xd.mapper;

import org.apache.ibatis.annotations.Param;
import org.feather.xd.model.CouponDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    /**
     * description: 扣减库存
     * @param couponId
     * @return {@link int}
     * @author: feather
     * @since: 2024-08-23 16:45
     **/
    int reduceStock(@Param("couponId") long couponId,@Param("num") int num);
}
