package com.example.mandarin_shop_back.entity.cart;

import com.example.mandarin_shop_back.dto.cart.response.CartRespDto;
import com.example.mandarin_shop_back.dto.product.response.AdminSearchProductRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private int cartId;
    private int userId;
    private int totalPrice;
    private LocalDate createDate;
    private LocalDate updateDate;

    public CartRespDto toSearchCartRespDto() {
        return CartRespDto.builder()
                .cartId(cartId)
                .userId(userId)
                .totalPrice(totalPrice)
                .build();
    }
}
