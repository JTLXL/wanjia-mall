package com.wanjia.cart.config;

import com.wanjia.common.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @author JT.L
 * @date 2020/6/7 16:09:23
 * @description
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "wj.jwt")
public class JwtProperties {

    /**
     * 公钥路径
     */
    private String pubKeyPath;

    /**
     * Cookie名称
     */
    private String cookieName;

    /**
     * 公钥
     */
    private PublicKey publicKey;


    /**
     * @PostContruct：在构造方法执行之后执行该方法
     * 对象一旦实例化后，就应该读取公钥
     */
    @PostConstruct
    public void init() {
        try {
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("读取公钥失败!", e);
            e.printStackTrace();
        }
    }
}
