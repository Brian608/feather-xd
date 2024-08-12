package org.feather.xd.bz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.feather.xd.UserApplication;
import org.feather.xd.util.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.bz
 * @className: MDTest
 * @author: feather
 * @description:
 * @since: 2024-08-11 17:33
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class MDTest {

    @Test
    public void test(){
        log.info("md5加密后:[{}]", CommonUtil.MD5("123456"));
       // String base64result="RTEwQURDMzk0OUJBNTlBQkJFNTZFMDU3RjIwRjg4M0U=";
    }
    @Test
    public void testMd5(){
         String base64result="RTEwQURDMzk0OUJBNTlBQkJFNTZFMDU3RjIwRjg4M0U=";
         String scret = "$1$i4Z8Z7PX";
        log.info("md5加密后:[{}]",Md5Crypt.md5Crypt(base64result.getBytes(), scret));

    }
}
