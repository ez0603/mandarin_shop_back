package com.example.mandarin_shop_back.dto.cart.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CartItemRespDto {
    private int cartItemId;
    private int cartId;
    private int productId;
    private int itemCount;
    private int itemPrice;
    private LocalDate createDate;
    private LocalDate updateDate;
}
