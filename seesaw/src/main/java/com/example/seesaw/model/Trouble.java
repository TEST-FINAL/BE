package com.example.seesaw.model;

import com.example.seesaw.dto.TroubleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@Entity
@AllArgsConstructor
public class Trouble extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column
    private Long views;

    @OneToMany(mappedBy = "trouble", cascade = CascadeType.REMOVE)
    private List<TroubleImage> troubleImages;

    @OneToMany(mappedBy = "trouble", cascade = CascadeType.REMOVE)
    private List<TroubleTag> troubleTags;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    public Trouble(String title, String contents, String question, String answer, Long views, User user){
        this.title = title;
        this.contents = contents;
        this.question = question;
        this.answer = answer;
        this.views = views;
        this.user = user;
    }


    public void update(TroubleDto troubleDto) {
        this.title = troubleDto.getTitle();
        this.contents = troubleDto.getContents();
        this.question = troubleDto.getQuestion();
        this.answer = troubleDto.getAnswer();
    }
}
