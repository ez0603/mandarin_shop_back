package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.account.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    public int saveAdmin(Admin admin);

    public int saveRole(@Param("adminId") int adminId, @Param("roleId") int roleId);

    public Admin findAdminByUsername(String username);

    public Admin findAdminByAdminId(int adminId);

    public Admin findAccountByNameAndEmail(@Param("adminName") String name, @Param("email") String email);
}
