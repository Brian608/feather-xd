package org.feather.xd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.model.ProductDO;
import org.feather.xd.model.ProductMessage;
import org.feather.xd.query.ProductQuery;
import org.feather.xd.request.LockProductRequest;
import org.feather.xd.vo.ProductVO;

import java.util.List;

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

    /**
     * 根据id批量查询商品
     * @param productIdList
     * @return
     */
    List<ProductVO> findProductsByIdBatch(List<Long> productIdList);

    /**
     * description: 锁定商品库存
     * 1.)遍历商品 锁定每个商品
     * 2.) 每一次锁定的时候，都要发送延迟消息
     * @param request
     * @return {@link Boolean}
     * @author: feather
     * @since: 2024-10-12 15:18
     **/

    Boolean lockProductStock(LockProductRequest request);

    /**
     * description: 释放商品库存
     * @param productMessage
     * @return {@link boolean}
     * @author: feather
     * @since: 2024-10-22 15:49
     **/
    boolean releaseProductStock(ProductMessage productMessage);
}
