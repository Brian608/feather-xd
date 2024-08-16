package org.feather.xd.service;

import org.feather.xd.model.AddressDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.request.AddressAddRequest;

/**
 * <p>
 * 电商-公司收发货地址表 服务类
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
public interface IAddressService extends IService<AddressDO> {

    /**
     * description: 添加收获地址
     * @param request
     * @return {@link Boolean}
     * @author: feather
     * @since: 2024-08-16 11:11
     **/
    Boolean add(AddressAddRequest request);

}
