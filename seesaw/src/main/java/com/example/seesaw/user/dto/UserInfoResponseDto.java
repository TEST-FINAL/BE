package com.example.seesaw.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {

    private String username;
    private String nickname;
    private String mbti;
    private String generation;
    private List<ProfileListDto> ProfileImages;
}
