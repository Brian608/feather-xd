package org.feather.xd.bz;

import lombok.extern.slf4j.Slf4j;
import org.feather.xd.UserApplication;
import org.feather.xd.service.IAddressService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.bz
 * @className: AddressTest
 * @author: feather
 * @description:
 * @since: 2024-08-09 17:00
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class AddressTest {

    @Autowired
    private IAddressService addressService;


}
