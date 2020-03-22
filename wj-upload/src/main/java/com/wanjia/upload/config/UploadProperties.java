package com.wanjia.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author JT.L
 * @date 2020/3/22 20:52:52
 * @description
 */
@Data
@ConfigurationProperties(prefix = "wj.upload")
public class UploadProperties {
    private String baseUrl;
    private List<String> allowTypes;
}
