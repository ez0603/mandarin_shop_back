package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.entity.product.OptionName;
import lombok.Data;

@Data
public class AddOptionNameReqDto {
    private int productId;
    private int optionTitleId;
    private String optionName;

    public OptionName toEntity() {
        return OptionName.builder()
                .productId(productId)
                .optionTitleId(optionTitleId)
                .optionName(optionName)
                .build();
    }
}
