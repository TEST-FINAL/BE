package com.example.seesaw.model;

import com.example.seesaw.game.Word;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Crossword {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private int x;                  // x좌표

    @Column
    private int y;                 // y좌표

    @Column
    private String word;         // 단어

    @Column
    private String contents;  // 내용

    @Column
    private int wordCount; // 글자수

    @Column
    private boolean isOriental; // 가로면 true, 세로면 false

    @ManyToOne
    @JoinColumn(name = "QuizNum_ID", nullable = false)
    private QuizNum quizNum;

    public Crossword(int x, int y, String word, String contents, int wordCount, boolean isOriental){
        this.x = x;
        this.y = y;
        this.word = word;
        this.contents = contents;
        this.wordCount = wordCount;
        this.isOriental = isOriental;
    }

    public Crossword (Word word, QuizNum quizNum){
        this.x = word.getX();
        this.y = word.getY();
        this.word = word.getName();
        this.contents = word.getClue();
        this.wordCount = word.getName().length();
        this.isOriental = word.getDirection().toString().equals("RIGHT");
        this.quizNum = quizNum;
    }

}
