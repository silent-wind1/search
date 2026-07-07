package com.yefeng.order.service.impl;

import com.yefeng.feign.api.UserFeignClient;
import com.yefeng.model.entity.Order;
import com.yefeng.model.entity.User;
import com.yefeng.order.mapper.OrderMapper;
import com.yefeng.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper ;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private RBloomFilter<String> orderIdBloomFilter;

    @Override
    public Order findOrderByOrderId(Long orderId) {
        // Redisson 布隆过滤器快速判断：如果说不存在，直接返回 null，避免无效查库
        if (!orderIdBloomFilter.contains(String.valueOf(orderId))) {
            log.info("Redisson 布隆过滤器判定订单 {} 不存在，跳过数据库查询", orderId);
            return null;
        }

        Order order = orderMapper.findOrderByOrderId(orderId);
        if (order != null) {
            User user = userFeignClient.queryById(order.getUserId());
            order.setUser(user);
            // 如果数据库中存在但过滤器未命中（极端情况），回补到过滤器中
            orderIdBloomFilter.add(String.valueOf(orderId));
        }
        return order;
    }

}