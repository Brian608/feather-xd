package org.feather.xd.service.impl;

import org.feather.xd.model.ProductDO;
import org.feather.xd.mapper.ProductMapper;
import org.feather.xd.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
