package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String pwd;
    private String pwdCheck;
    private String nickname;
    private String generation;
    private boolean admin = false;
    private String adminToken = "";
}
