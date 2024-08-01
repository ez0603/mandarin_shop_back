package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.entity.product.OptionTitle;
import lombok.Data;

@Data
public class AddOptionTitleReqDto {
    private int productId;
    private int menuId;
    private String titleName;

    public OptionTitle toEntity() {
        return OptionTitle.builder()
                .productId(productId)
                .titleName(titleName)
                .build();
    }
}
