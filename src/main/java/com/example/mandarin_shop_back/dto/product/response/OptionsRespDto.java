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
public class OptionsRespDto {
    private int productId;
    private int optionTitleId;
    private String titleName;
    private String optionName;

    private List<Integer> optionNameIds;
    private List<String> optionNames;
}
