package com.example.mandarin_shop_back.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRespDto {
    private String productName;
    private int categoryId;
    private String categoryName;
    private int productPrice;
    private String productImg;
    private String productDescription;
    private List<OptionDetail> optionList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionDetail {
        private int optionTitleId;
        private String optionTitleName;
        private List<Integer> optionNameIds;
        private List<String> optionNames;
        private List<Integer> optionPrices;
    }
}
