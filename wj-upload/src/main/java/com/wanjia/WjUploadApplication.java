package com.wanjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author JT.L
 * @date 2020/3/19 18:06:33
 * @description
 */
@EnableDiscoveryClient
@SpringBootApplication
public class WjUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjUploadApplication.class);
    }
}
