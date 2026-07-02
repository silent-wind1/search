package com.yefeng.order.service.impl;

import com.yefeng.feign.api.UserFeignClient;
import com.yefeng.model.entity.Order;
import com.yefeng.model.entity.User;
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
        User user = userFeignClient.queryById(order.getUserId());
        order.setUser(user);
        return order;
    }

}