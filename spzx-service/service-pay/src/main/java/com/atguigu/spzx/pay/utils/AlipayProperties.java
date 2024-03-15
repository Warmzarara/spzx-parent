package com.atguigu.spzx.pay.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.pay.utils
 * @className: AlipayProperties
 * @author: XiaoHB
 * @date: 2024/3/15 19:12
 */
@Data
@ConfigurationProperties(prefix = "spzx.alipay")
public class AlipayProperties {
    private String alipayUrl;
    private String appPrivateKey;
    public  String alipayPublicKey;
    private String appId;
    public  String returnPaymentUrl;
    public  String notifyPaymentUrl;

    public final static String format="json";
    public final static String charset="utf-8";
    public final static String sign_type="RSA2";
}
