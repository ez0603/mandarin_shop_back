package com.example.mandarin_shop_back.entity.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleRegister {
    private int roleAdminRegisterId;
    private int adminId;
    private int roleId;
    private LocalDate createDate;
    private LocalDate updateDate;

    private Role role;
}
