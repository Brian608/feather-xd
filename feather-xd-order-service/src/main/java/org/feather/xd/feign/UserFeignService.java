package org.feather.xd.feign;

import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.AddressVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.feign
 * @className: UserFeignService
 * @author: feather
 * @description:
 * @since: 2024-10-24 21:34
 * @version: 1.0
 */
@FeignClient(name = "feather-xd-user-service")
public interface UserFeignService {

    /**
     * description: 查询接口权限
     * @param addressId
     * @return {@link JsonResult}
     * @author: feather
     * @since: 2024-10-24 21:41
     **/
    @GetMapping("/api/address/v1//find/{address_id}")
    JsonResult<AddressVO> detail(@PathVariable("address_id")long addressId);


}
