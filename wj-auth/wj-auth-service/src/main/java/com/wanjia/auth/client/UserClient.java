package com.wanjia.auth.client;

import com.wanjia.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author JT.L
 * @date 2020/5/30 12:58:27
 * @description
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
