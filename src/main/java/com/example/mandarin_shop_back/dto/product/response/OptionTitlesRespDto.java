package com.example.mandarin_shop_back.dto.product.response;

import com.example.mandarin_shop_back.dto.product.request.AddOptionNameReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionTitlesRespDto {
    private List<Integer> optionTitlesId;
    private List<String> optionTitleNames; // 여러 옵션 타이틀 이름을 저장하는 리스트
    private List<AddOptionNameReqDto> optionNames; // 옵션 이름 리스트
}
