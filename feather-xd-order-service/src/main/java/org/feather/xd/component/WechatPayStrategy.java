package org.feather.xd.component;

import lombok.extern.slf4j.Slf4j;
import org.feather.xd.vo.PayInfoVO;
import org.springframework.stereotype.Service;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.component
 * @className: WechatPayStrategy
 * @author: feather
 * @description:
 * @since: 2024-12-23 21:18
 * @version: 1.0
 */
@Slf4j
@Service
public class WechatPayStrategy implements  PayStrategy{
    @Override
    public String unifiedOrder(PayInfoVO payInfoVO) {
        return null;
    }

    @Override
    public String refound(PayInfoVO payInfoVO) {
        return PayStrategy.super.refound(payInfoVO);
    }

    @Override
    public String queryOrderPaySuccess(PayInfoVO payInfoVO) {
        return PayStrategy.super.queryOrderPaySuccess(payInfoVO);
    }
}
