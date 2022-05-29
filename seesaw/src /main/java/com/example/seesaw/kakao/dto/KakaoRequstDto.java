package com.example.seesaw.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoRequstDto {

    private String kakaoId;
    private String username;
    private String nickname;
    private String generation;
    private String energy;
    private String insight;
    private String judgement;
    private String lifePattern;
    private List<Long> charId;
    private boolean admin = false;
    private String adminToken = "";

}
