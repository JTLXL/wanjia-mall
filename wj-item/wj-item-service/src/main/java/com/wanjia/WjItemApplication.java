package com.wanjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author JT.L
 * @date 2020/3/12 17:33:28
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.wanjia.item.mapper")
public class WjItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjItemApplication.class, args);
    }
}
