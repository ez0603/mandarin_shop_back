package com.example.mandarin_shop_back.dto.product.request;

import com.example.mandarin_shop_back.entity.product.OptionTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteOptionTitleReqDto {
    private int productId;
    private int optionTitleId;

    public OptionTitle toEntity() {
        return OptionTitle.builder()
                .productId(productId)
                .optionTitleId(optionTitleId)
                .build();
    }
}
