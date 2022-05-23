package com.example.seesaw.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrossWordRequestDto {
    private Long id;                 // 단어 id
    private int x;                  // x좌표 (시작 좌표)
    private int y;                 // y좌표  (시작 좌표)
    private String word;         // 단어
    private boolean isOriental; // 가로면 true, 세로면 false
    private Long quizId;


}
