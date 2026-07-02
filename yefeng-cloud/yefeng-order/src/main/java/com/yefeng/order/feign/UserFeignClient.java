package com.yefeng.order.feign;

import com.yefeng.order.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value="yefeng-user",
        contextId = "UserFeignClient"
)
public interface UserFeignClient {
    @GetMapping("/api/user/findUserByUserId/{userId}")
    User queryById(@PathVariable("userId") Long userId) ;
}
