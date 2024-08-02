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
    private List<Integer> optionTitlesId;
    private List<String> optionTitleNames;

    private List<Integer> optionNameIds;
    private List<String> optionNames;
}
