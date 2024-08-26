package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.entity.product.Product;
import lombok.Data;

import java.util.List;

@Data
public class AdminRegisterProductReqDto {
    private String productName;
    private int categoryId;
    private int productPrice;
    private String productImg;
    private String productDescription;

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .categoryId(categoryId)
                .productPrice(productPrice)
                .productImg(productImg)
                .productDescription(productDescription)
                .build();
    }
}