package com.yefeng.order.controller;

import com.yefeng.common.config.UserThreadLocal;
import com.yefeng.model.entity.Order;
import com.yefeng.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService ;

    @GetMapping(value = "/findOrderByOrderId/{orderId}")
    public Order findOrderByOrderId(@PathVariable Long orderId) {
        Long user = UserThreadLocal.getUser();
        log.info("user:{}", user);
        return orderService.findOrderByOrderId(orderId) ;
    }

}