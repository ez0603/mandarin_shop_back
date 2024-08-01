package com.example.mandarin_shop_back.entity.cart;

import com.example.mandarin_shop_back.dto.cart.response.CartItemRespDto;
import com.example.mandarin_shop_back.dto.cart.response.CartRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private int cartItemId;
    private int cartId;
    private int productId;
    private int itemCount;
    private int itemPrice;
    private LocalDate createDate;
    private LocalDate updateDate;

    public CartItemRespDto toSearchCartItemRespDto() {
        return CartItemRespDto.builder()
                .cartItemId(cartItemId)
                .cartId(cartId)
                .productId(productId)
                .itemCount(itemCount)
                .itemPrice(itemPrice)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }

}
