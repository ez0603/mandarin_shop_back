package com.example.mandarin_shop_back.entity.product;

import com.example.mandarin_shop_back.dto.product.response.OptionsRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionName {
    private int optionNameId;
    private int productId;
    private int optionTitleId;
    private String optionName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private OptionTitle optionTitle;
    private Product product;

    public OptionsRespDto toOptionRespDto() {
        return OptionsRespDto.builder()
                .optionTitleId(optionTitleId)
                .productId(productId)
                .optionName(optionName)
                .optionTitleId(optionTitle.getOptionTitleId())
                .build();
    }
}
