package com.example.seesaw.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Setter
@Entity
@AllArgsConstructor
public class Mbti {

    @Id
    @Column
    private Long id;

    @Column
    private String mbtiName;

    @Column
    private String detail;
}
