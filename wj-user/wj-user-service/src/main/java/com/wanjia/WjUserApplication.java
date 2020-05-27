package com.wanjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author JT.L
 * @date 2020/5/26 12:02:01
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.wanjia.user.mapper")
public class WjUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjUserApplication.class, args);
    }
}
