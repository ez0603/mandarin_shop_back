package com.example.mandarin_shop_back.dto.order.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderRespDto {
    private int orderId;
    private int userId;
    private int cartId;
    private LocalDateTime orderDate;
    private int orderTotalPrice;
    private String shippingAddress;
    private int orderStatusId;
    private LocalDateTime createDate;
}
