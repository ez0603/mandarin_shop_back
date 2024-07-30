package com.example.mandarin_shop_back.dto.account.request;

import com.example.mandarin_shop_back.entity.account.Admin;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class AdminSignupReqDto {

    @Pattern(regexp = "^[가-힣a-zA-Z\\s.]{2,25}$",message = "이름에는 숫자, 특수문자가 들어갈 수 없습니다.")
    private String name;
    @Pattern(regexp = "^[A-Za-z0-9]{4,10}$", message = "아이디는 영문자, 숫자 5 ~ 10자리 형식이어야 합니다.")
    private String adminName;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{7,128}$",message = "비밀번호는 영문자, 숫자, 특수문자를 포함한 5 ~ 128자리 형식이어야 합니다.")
    private String adminPassword;
    @Email(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{1,3}$",message = "이메일 형식이어야 합니다.") //0314-2
    private String email;

    public Admin toEntity(BCryptPasswordEncoder passwordEncoder){
        return Admin.builder()
                .name(name)
                .adminName(adminName)
                .adminPassword(passwordEncoder.encode(adminPassword))
                .email(email)
                .build();
    }

}
