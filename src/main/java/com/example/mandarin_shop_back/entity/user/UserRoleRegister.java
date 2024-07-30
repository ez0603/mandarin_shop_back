package com.example.mandarin_shop_back.entity.user;

import com.example.mandarin_shop_back.entity.account.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRegister {
    private int roleRegisterId;
    private int userId;
    private int roleId;
    private LocalDate createDate;
    private LocalDate updateDate;

    private Role role;
}