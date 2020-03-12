package com.wanjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author JT.L
 * @date 2020/3/11 17:24:39
 * @description
 */

@EnableEurekaServer
@SpringBootApplication
public class WjRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjRegistryApplication.class);
    }
}
