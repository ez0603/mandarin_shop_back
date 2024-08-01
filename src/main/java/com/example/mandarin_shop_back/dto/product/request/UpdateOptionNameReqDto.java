package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.entity.product.OptionName;
import lombok.Data;

@Data
public class UpdateOptionNameReqDto {
    private int optionNameId;
    private int productId;
    private int optionTitleId;
    private String optionName;
    private int optionPrice;

    public OptionName toEntity() {
        return OptionName.builder()
                .optionNameId(optionNameId)
                .productId(productId)
                .optionTitleId(optionTitleId)
                .optionName(optionName)
                .build();
    }
}
