package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.order.Order;
import com.example.mandarin_shop_back.entity.order.OrderItem;
import com.example.mandarin_shop_back.entity.order.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    public int saveOrderStatus(OrderStatus orderStatus);

    public int saveOrder(Order order);

    public List<Order> findOrders();

    public List<Order> findOrdersByUserId(@Param("userId") int userId);

    public int saveOrderItem(OrderItem orderItem);

    public List<OrderItem> findOrderItem();

    public int cancelOrder(@Param("orderId") int orderId);

    public int deleteOrderItem(@Param("orderId") int orderId);
}
