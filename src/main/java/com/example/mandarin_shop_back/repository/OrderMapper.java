package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.order.Order;
import com.example.mandarin_shop_back.entity.order.OrderStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    public int saveOrderStatus(OrderStatus orderStatus);

    public int saveOrder(Order order);

    public List<Order> findOrders();
}
