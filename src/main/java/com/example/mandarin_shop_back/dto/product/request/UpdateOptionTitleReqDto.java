package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.entity.product.OptionTitle;
import lombok.Data;

@Data
public class UpdateOptionTitleReqDto {
    private int optionTitleId;
    private int productId;
    private String titleName;

    public OptionTitle toEntity() {
        return OptionTitle.builder()
                .optionTitleId(optionTitleId)
                .productId(productId)
                .titleName(titleName)
                .build();
    }
}