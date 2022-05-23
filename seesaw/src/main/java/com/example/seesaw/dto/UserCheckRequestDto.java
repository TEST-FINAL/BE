package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserCheckRequestDto {

    private String username;
    private String pwd;
    private String pwdCheck;

}
