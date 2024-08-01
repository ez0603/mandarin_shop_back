package com.example.mandarin_shop_back.entity.order;

import com.example.mandarin_shop_back.dto.order.response.OrderItemRespDto;
import com.example.mandarin_shop_back.dto.order.response.OrderRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int productId;
    private int count;
    private int price;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public OrderItemRespDto toOrderItemRespDto () {
        return OrderItemRespDto.builder()
                .orderId(orderId)
                .productId(productId)
                .count(count)
                .price(price)
                .build();
    }
}
