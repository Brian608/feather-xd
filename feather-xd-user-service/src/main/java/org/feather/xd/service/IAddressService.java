package org.feather.xd.service;

import org.feather.xd.model.AddressDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.request.AddressAddRequest;
import org.feather.xd.vo.AddressVO;

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

    /**
     * description: 地址详情
     * @param id
     * @return {@link AddressVO}
     * @author: feather
     * @since: 2024-08-16 15:42
     **/
    AddressVO detail(Long id);

    /**
     * description: 通过id删除地址
     * @param id
     * @return {@link Boolean}
     * @author: feather
     * @since: 2024-08-17 16:30
     **/
    Boolean del(Long id);
}
