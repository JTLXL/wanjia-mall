package com.wanjia.auth.config;

import com.wanjia.common.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author JT.L
 * @date 2020/5/29 20:13:24
 * @description
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "wanjia.jwt")
public class JwtProperties {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 公钥路径
     */
    private String pubKeyPath;

    /**
     * 私钥路径
     */
    private String priKeyPath;

    /**
     * token过期时间
     */
    private int expire;

    /**
     * Cookie名称
     */
    private String cookieName;

    /**
     * Cookie生存时间
     */
    private Integer cookieMaxAge;

    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * 私钥
     */
    private PrivateKey privateKey;


    /**
     * @PostContruct：在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init(){
        try {
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException();
        }
    }

}
