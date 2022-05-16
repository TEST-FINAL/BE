package com.example.seesaw.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Setter
@Entity
@AllArgsConstructor
public class TroubleTag {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "TROUBLE_ID", nullable = false)
    private Trouble trouble;

    public TroubleTag(String tagName, User user, Trouble trouble){
        this.tagName = tagName;
        this.user = user;
        this.trouble = trouble;

    }
}
