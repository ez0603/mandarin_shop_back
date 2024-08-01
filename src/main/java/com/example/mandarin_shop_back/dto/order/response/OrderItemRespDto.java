package com.example.mandarin_shop_back.dto.order.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemRespDto {
    private int orderId;
    private int productId;
    private int count;
    private int price;
}
