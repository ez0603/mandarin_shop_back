package com.example.mandarin_shop_back.entity.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private int roleId;
    private String roleName;
    private String roleNameKor;
    private LocalDate createDate;
    private LocalDate updateDate;

}
