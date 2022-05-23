package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiRequestDto {

    private String id;
    private String energy;
    private String insight;
    private String judgement;
    private String lifePattern;
}
