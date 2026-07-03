package com.yefeng.feign.interceptor;

import com.yefeng.common.config.UserThreadLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class CommonFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Long user = UserThreadLocal.getUser();

        template.header("UserId", String.valueOf(user));
    }
}
