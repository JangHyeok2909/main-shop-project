package com.example.main_shop_project.dto.login;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    private String loginId;
    private String password;
}
