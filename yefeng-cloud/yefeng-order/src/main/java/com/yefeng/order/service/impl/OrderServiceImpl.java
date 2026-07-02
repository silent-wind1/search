package com.yefeng.order.service.impl;

import com.yefeng.order.entity.Order;
import com.yefeng.order.entity.User;
import com.yefeng.order.feign.UserFeignClient;
import com.yefeng.order.mapper.OrderMapper;
import com.yefeng.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper ;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Order findOrderByOrderId(Long orderId) {
        Order order = orderMapper.findOrderByOrderId(orderId);
        User user = userFeignClient.queryById(orderId);
        order.setUser(user);
        return order;
    }

}