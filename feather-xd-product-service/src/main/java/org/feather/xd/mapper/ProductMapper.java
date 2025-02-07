package org.feather.xd.mapper;

import org.apache.ibatis.annotations.Param;
import org.feather.xd.model.ProductDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author feather
 * @since 2024-09-05
 */
public interface ProductMapper extends BaseMapper<ProductDO> {

    /**
     * 锁定商品库存
     * @param productId
     * @param buyNum
     * @return
     */
    int lockProductStock(@Param("productId") long productId, @Param("buyNum") int buyNum);

    /**
     * description:  解锁商品存储
     * @param productId
     * @param buyNum
     * @return
     * @author: feather
     * @since: 2024-10-22 16:35
     **/
    void unlockProductStock(Long productId, Integer buyNum);
}
