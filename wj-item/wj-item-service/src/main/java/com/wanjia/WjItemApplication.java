package com.wanjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author JT.L
 * @date 2020/3/12 17:33:28
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WjItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjItemApplication.class, args);
    }
}
