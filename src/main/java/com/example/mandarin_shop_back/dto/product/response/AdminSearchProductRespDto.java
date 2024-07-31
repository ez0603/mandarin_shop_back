package com.example.mandarin_shop_back.dto.product.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdminSearchProductRespDto {
    private int productId;
    private String productName;
    private int categoryId;
    private String categoryName;
    private int productPrice;
    private String productImg;
    private String productDescription;
}
