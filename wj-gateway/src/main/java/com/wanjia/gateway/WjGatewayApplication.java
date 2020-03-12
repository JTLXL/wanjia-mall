package com.wanjia.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author JT.L
 * @date 2020/3/11 18:23:07
 * @description
 */
@EnableZuulProxy
@SpringCloudApplication
public class WjGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjGatewayApplication.class);
    }
}
