package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String pwd;
    private String generation;
    private String energy;
    private String insight;
    private String judgement;
    private String lifePattern;
    private String nickname;
    private List<Long> charId;
    private boolean admin = false;
    private String adminToken = "";
}
