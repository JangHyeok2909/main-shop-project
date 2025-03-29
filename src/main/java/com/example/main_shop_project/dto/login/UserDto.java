package com.example.main_shop_project.dto.login;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {
    private String loginId;
    private String password;
    private String name;
    private String nickname;
}
