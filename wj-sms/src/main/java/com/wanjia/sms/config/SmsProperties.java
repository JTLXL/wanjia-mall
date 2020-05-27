package com.wanjia.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author JT.L
 * @date 2020/5/24 17:19:51
 * @description
 */
@Data
@ConfigurationProperties(prefix = "wj.sms")
public class SmsProperties {
    String accessKeyId;

    String accessKeySecret;

    String signName;

    String verifyCodeTemplate;
}
