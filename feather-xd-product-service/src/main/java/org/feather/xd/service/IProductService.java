package org.feather.xd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.model.ProductDO;
import org.feather.xd.query.ProductQuery;
import org.feather.xd.vo.ProductVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feather
 * @since 2024-09-05
 */
public interface IProductService extends IService<ProductDO> {


    /**
     * description: 首页产品信息
     * @param query
     * @return {@link Page<ProductVO>}
     * @author: feather
     * @since: 2024-09-06 16:00
     **/
    Page<ProductVO> pageProduct(ProductQuery query);

    /**
     * description: 根据id查询商品详情
     * @param productId
     * @return {@link ProductVO}
     * @author: feather
     * @since: 2024-09-06 16:16
     **/
    ProductVO findDetailById(long productId);

}
