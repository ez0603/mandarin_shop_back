package com.example.mandarin_shop_back.dto.cart.request;

import com.example.mandarin_shop_back.entity.cart.Cart;
import com.example.mandarin_shop_back.entity.product.Product;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CartReqDto {
    private int cartId;
    private int userId;
    private int totalPrice;
    private LocalDate createDate;
    private LocalDate updateDate;

    public Cart toEntity() {
        return Cart.builder()
                .cartId(cartId)
                .userId(userId)
                .totalPrice(totalPrice)
                .build();
    }
}
