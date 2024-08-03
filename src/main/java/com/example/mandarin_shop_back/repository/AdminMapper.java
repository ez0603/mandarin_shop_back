package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.account.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    public int saveAdmin(Admin admin);

    public int saveRole(@Param("adminId") int adminId, @Param("roleId") int roleId);

    public Admin findAdminByUsername(String adminName);

    public Admin findAdminByAdminId(int adminId);

    public int modifyPassword(Admin admin);

    public Admin findAccountByNameAndEmail(@Param("name") String name, @Param("email") String email);

    public Admin findAccountByUserNameAndEmail(@Param("adminName") String adminName, @Param("email") String email);

    public int updateAccountTemporaryPw(@Param("adminId") int adminId, @Param("tempPassword") String tempPassword);
}
