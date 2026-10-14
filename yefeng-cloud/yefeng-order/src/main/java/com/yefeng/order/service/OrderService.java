package com.yefeng.order.service;

import com.yefeng.model.entity.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderService {

    // 根据订单的id查询订单数据
    Order findOrderByOrderId(Long orderId) ;

    int addOrder(@Param("order") Order order);
}