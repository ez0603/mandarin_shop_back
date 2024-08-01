package com.example.mandarin_shop_back.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionTitle {
    private int optionTitleId;
    private int productId;
    private String titleName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
