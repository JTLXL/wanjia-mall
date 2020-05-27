package com.jtl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author JT.L
 * @date 2020/4/26 18:31:44
 * @description
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class WjPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjPageApplication.class);
    }
}
