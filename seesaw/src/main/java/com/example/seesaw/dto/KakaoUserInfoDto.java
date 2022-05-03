package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;
}
