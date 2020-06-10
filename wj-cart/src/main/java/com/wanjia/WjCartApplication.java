package com.wanjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author JT.L
 * @date 2020/6/7 13:06:43
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WjCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjCartApplication.class, args);
    }
}
