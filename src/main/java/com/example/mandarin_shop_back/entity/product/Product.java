package com.example.mandarin_shop_back.entity.product;


import com.example.mandarin_shop_back.dto.product.response.AdminSearchProductRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int productId;
    private String productName;
    private int categoryId;
    private String categoryName;
    private int productPrice;
    private String productImg;
    private String productDescription;
    private int optionTitleId;
    private String titleName;
    private int optionNameId;
    private String optionName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public AdminSearchProductRespDto toSearchProductRespDto() {
        return AdminSearchProductRespDto.builder()
                .productId(productId)
                .categoryId(categoryId)
                .productName(productName)
                .productPrice(productPrice)
                .productImg(productImg)
                .productDescription(productDescription)
                .categoryName(categoryName)
                .optionTitleId(optionTitleId)
                .titleName(titleName)
                .optionNameId(optionNameId)
                .optionName(optionName)
                .build();
    }
}