package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.entity.product.Product;
import lombok.Data;

import java.util.List;

@Data
public class UpdateProductReqDto {
    private int productId;
    private String productName;
    private int categoryId;
    private String categoryName;
    private int productPrice;
    private String productImg;
    private String productDescription;

    public Product toEntity() {
        return Product.builder()
                .productId(productId)
                .productName(productName)
                .categoryId(categoryId)
                .categoryName(categoryName)
                .productPrice(productPrice)
                .productImg(productImg)
                .productDescription(productDescription)
                .build();
    }

}
