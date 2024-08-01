package com.example.mandarin_shop_back.dto.cart.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CartRespDto {
    private int cartId;
    private int userId;
    private int totalPrice;
    private LocalDate createDate;
    private LocalDate updateDate;
}
