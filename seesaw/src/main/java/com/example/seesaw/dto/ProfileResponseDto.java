package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

    private List<ProfileListDto> faceUrl;
    private List<ProfileListDto> accessoryUrl;
    private List<ProfileListDto> backgroundUrl;

}