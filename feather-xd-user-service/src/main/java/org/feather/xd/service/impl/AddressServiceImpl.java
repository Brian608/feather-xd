package org.feather.xd.service.impl;

import org.feather.xd.model.AddressDO;
import org.feather.xd.mapper.AddressMapper;
import org.feather.xd.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 电商-公司收发货地址表 服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressDO> implements IAddressService {

}
