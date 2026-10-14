package com.yefeng.order.controller;

import com.yefeng.common.config.UserThreadLocal;
import com.yefeng.model.entity.Order;
import com.yefeng.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/order")
@RefreshScope
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${name}")
    private String name;

    @GetMapping(value = "/findOrderByOrderId/{orderId}")
    public Order findOrderByOrderId(@PathVariable Long orderId) {
        Long user = UserThreadLocal.getUser();
        log.info("user:{}", user);
        log.info("name:{}", name);
        return orderService.findOrderByOrderId(orderId);
    }


    @PostMapping(value = "/add")
    public String addOrder(@RequestBody Order order) {
        Long userId = UserThreadLocal.getUser();
        order.setUserId(userId);
        int result = orderService.addOrder(order);
        if (result > 0) {
            return "success";
        }
        return "fail";
    }

}