package com.example.seesaw.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class TroubleLike{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="TROUBLE_ID", nullable = false)
    private TroubleComment troubleComment;


    public TroubleLike(User user, TroubleComment troubleComment){
        this.user = user;
        this.troubleComment = troubleComment;
    }
}
