package com.example.mandarin_shop_back.service.admin;

import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.repository.AdminMapper;
import com.example.mandarin_shop_back.security.PrincipalAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements UserDetailsService {

    private final AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        Admin admin = adminMapper.findAdminByUsername(adminName);
        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found with username: " + adminName);
        }
        return new PrincipalAdmin(admin);
    }
}
