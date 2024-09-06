package org.feather.xd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.model.ProductDO;
import org.feather.xd.mapper.ProductMapper;
import org.feather.xd.query.ProductQuery;
import org.feather.xd.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-09-05
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements IProductService {

    @Override
    public Page<ProductVO> pageProduct(ProductQuery query) {
        Page<ProductDO> page=new Page<>(query.getPageNo(),query.getPageSize());
        LambdaQueryWrapper<ProductDO> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(ProductDO::getCreateTime);
        Page<ProductDO> productDOPage= this.page(page,queryWrapper);
        List<ProductDO> records = productDOPage.getRecords();
        records.forEach(p->p.setStock(p.getStock()-p.getLockStock()));
        Page<ProductVO> productVOPage = new Page<>(query.getPageNo(), query.getPageSize(), productDOPage.getTotal());
        productVOPage.setRecords(BeanUtil.copyToList(records, ProductVO.class));
        return productVOPage;
    }

    @Override
    public ProductVO findDetailById(long productId) {
        ProductDO productDO = Optional.ofNullable(this.getById(productId)).orElseThrow(() -> new BizException("商品不存在"));
        return BeanUtil.copyProperties(productDO, ProductVO.class);
    }
}
