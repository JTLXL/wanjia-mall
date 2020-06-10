package com.wanjia.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author JT.L
 * @date 2020/6/2 16:03:01
 * @description
 */
@Data
@ConfigurationProperties(prefix = "wanjia.filter")
public class FilterProperties {

    private List<String> allowPaths;
}
