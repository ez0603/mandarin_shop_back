package com.example.mandarin_shop_back.entity.cart;

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
}
