package com.example.mandarin_shop_back.entity.order;

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
public class Order {
    private int orderId;
    private int userId;
    private int cartId;
    private LocalDateTime orderDate;
    private int orderTotalPrice;
    private String shippingAddress;
    private int orderStatusId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public OrderRespDto toOrderRespDto () {
        return OrderRespDto.builder()
                .userId(userId)
                .cartId(cartId)
                .orderDate(orderDate)
                .orderTotalPrice(orderTotalPrice)
                .shippingAddress(shippingAddress)
                .orderStatusId(orderStatusId)
                .build();
    }
}
