package com.example.seesaw.crossword.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizNum {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long quizNumber;

    @OneToMany(mappedBy = "quizNum",cascade = CascadeType.ALL)
    @Column
    private List<Crossword> crosswords;

    public QuizNum(Long quizNumber, List<Crossword> crossword) {
        this.quizNumber = quizNumber;
        this.crosswords = crossword;
    }

    // 목업용 (지울예정)
    public QuizNum(Long id){
        this.id = id;
    }
}
