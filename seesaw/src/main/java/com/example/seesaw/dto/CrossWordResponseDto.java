package com.example.seesaw.dto;

import com.example.seesaw.game.Word;
import lombok.Getter;
import lombok.Setter;
import com.example.seesaw.model.Crossword;

@Getter
@Setter
public class CrossWordResponseDto {
    private int id;                 // 단어 id
    private int x;                  // x좌표 (시작 좌표)
    private int y;                 // y좌표  (시작 좌표)
    private String word;         // 단어
    private String contents;  // 내용
    private int wordCount; // 글자수
    private boolean isOriental; // 가로면 true, 세로면 false
    private boolean pass; // 문제 정답 status

    public CrossWordResponseDto(int id, Word word, boolean pass){
        this.id = id;
        this.x = word.getX();
        this.y = word.getY();
        this.word = word.getName();
        this.contents = word.getClue();
        this.wordCount = word.getName().length();
        this.isOriental = word.getDirection().toString().equals("RIGHT");
        this.pass = pass;
    }
}

