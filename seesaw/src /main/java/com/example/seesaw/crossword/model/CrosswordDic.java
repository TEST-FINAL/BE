package com.example.seesaw.crossword.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CrosswordDic {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

}
