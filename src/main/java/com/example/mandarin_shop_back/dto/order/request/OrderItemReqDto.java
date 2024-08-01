package com.example.mandarin_shop_back.dto.order.request;

import com.example.mandarin_shop_back.entity.order.Order;
import com.example.mandarin_shop_back.entity.order.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderItemReqDto {
    private int orderId;
    private int productId;
    private int count;
    private int price;

    public OrderItem toEntity() {
        return OrderItem.builder()
                .orderId(orderId)
                .productId(productId)
                .count(count)
                .price(price)
                .build();
    }
}
