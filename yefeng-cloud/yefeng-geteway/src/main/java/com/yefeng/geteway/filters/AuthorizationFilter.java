package com.yefeng.geteway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @author wind
 * @description: 自定义全局过滤器，一般用来实现用户鉴权
 * @date 2026/7/3 17:00
 */
@Slf4j
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("-----------自定义全局过滤器-----------");
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String path = uri.getPath();
        log.info("path:{}", path);

        // 模拟一下鉴权
        String token = null;
        List<String> list = request.getHeaders().get("token");
        if(list != null && !list.isEmpty()){
            token = list.getFirst();
        } else {
            token = "123456";
        }

        // 鉴权通过放行
        // ServerHttpRequest 是不可变的，必须通过 builder.build() 创建新实例
        ServerHttpRequest mutatedRequest = request.mutate()
                .header("token", token)
                .build();
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
