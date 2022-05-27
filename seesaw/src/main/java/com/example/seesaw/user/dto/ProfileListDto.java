package com.example.seesaw.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileListDto {

    private Long charId;
    private String profileImage;     // Url -> profileImage

}