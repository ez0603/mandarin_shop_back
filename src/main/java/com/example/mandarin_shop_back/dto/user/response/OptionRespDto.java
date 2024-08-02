package com.example.mandarin_shop_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionRespDto {
    private int adminId;
    private int menuId;
    private int optionTitleId;
    private String optionTitleName;
    private int optionNameId;
    private String optionName;
    private int optionPrice;
    private String menuImgUrl;
    private String menuName;
    private int menuPrice;

}
