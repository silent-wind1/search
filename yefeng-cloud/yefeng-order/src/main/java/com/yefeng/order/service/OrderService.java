package com.yefeng.order.service;

import com.yefeng.model.entity.Order;

public interface OrderService {

    // 根据订单的id查询订单数据
    public abstract Order findOrderByOrderId(Long orderId) ;

}