package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.entity.product.OptionName;
import com.example.mandarin_shop_back.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public int saveUser(User user);

    public List<User> getUserAuth();

    public User findUserByUsername(String username);

    public User findUserByUserId(int userId);

    public int modifyUserPassword(User user);

    public User findAccountUserByNameAndEmail(@Param("customerName") String name, @Param("email") String email);

    public int updateUserAccountTemporaryPw(@Param("userId") int adminId, @Param("tempPassword") String tempPassword);

    public User findAccountByNameAndEmail(@Param("username") String username, @Param("email") String email);
}
