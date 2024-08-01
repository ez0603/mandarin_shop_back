package com.example.mandarin_shop_back.dto.cart.request;

import com.example.mandarin_shop_back.entity.cart.Cart;
import com.example.mandarin_shop_back.entity.cart.CartItem;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CartItemReqDto {
    private int cartItemId;
    private int cartId;
    private int productId;
    private int itemCount;
    private int itemPrice;
    private LocalDate createDate;
    private LocalDate updateDate;

    public CartItem toEntity() {
        return CartItem.builder()
                .cartItemId(cartItemId)
                .cartId(cartId)
                .productId(productId)
                .itemCount(itemCount)
                .itemPrice(itemPrice)
                .build();
    }
}
