package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.entity.product.Category;
import lombok.Data;

@Data
public class UpdateProductCategoryReqDto {
    private int categoryId;
    private String categoryName;

    public Category toEntity() {
        return Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }
}
