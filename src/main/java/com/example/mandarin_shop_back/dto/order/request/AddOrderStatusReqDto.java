package com.example.mandarin_shop_back.dto.order.request;

import com.example.mandarin_shop_back.entity.order.OrderStatus;
import com.example.mandarin_shop_back.entity.product.Category;
import lombok.Data;

@Data
public class AddOrderStatusReqDto {
    private int orderStatusId;
    private String statusName;

    public OrderStatus toEntity() {
        return OrderStatus.builder()
                .orderStatusId(orderStatusId)
                .statusName(statusName)
                .build();
    }
}