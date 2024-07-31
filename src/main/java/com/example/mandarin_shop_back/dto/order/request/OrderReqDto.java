package com.example.mandarin_shop_back.dto.order.request;

import com.example.mandarin_shop_back.entity.order.Order;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderReqDto {
    private int userId;
    private LocalDateTime orderDate;
    private int orderTotalPrice;
    private String shippingAddress;
    private int orderStatusId;

    public Order toEntity() {
        return Order.builder()
                .userId(userId)
                .orderDate(orderDate)
                .orderTotalPrice(orderTotalPrice)
                .shippingAddress(shippingAddress)
                .orderStatusId(orderStatusId)
                .build();
    }
}
