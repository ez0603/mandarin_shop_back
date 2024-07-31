package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.order.OrderStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    public int saveOrderStatus(OrderStatus orderStatus);
}
