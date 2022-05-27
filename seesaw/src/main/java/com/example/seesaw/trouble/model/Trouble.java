package com.example.seesaw.trouble.model;

import com.example.seesaw.trouble.dto.TroubleDto;
import com.example.seesaw.dictionary.model.Timestamped;
import com.example.seesaw.user.model.User;
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

    @OneToMany(mappedBy = "trouble", cascade = CascadeType.ALL)
    private List<TroubleImage> troubleImages;

    @OneToMany(mappedBy = "trouble", cascade = CascadeType.ALL)
    private List<TroubleTag> troubleTags;

    @OneToMany(mappedBy = "trouble", cascade = CascadeType.ALL)
    private List<TroubleComment> troubleComments;

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
