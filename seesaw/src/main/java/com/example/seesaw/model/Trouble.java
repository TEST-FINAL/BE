package com.example.seesaw.model;

import com.example.seesaw.dto.TroubleRequestDto;
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
    private String content;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column
    private Long viewCount;

    @OneToMany(mappedBy = "trouble", cascade = CascadeType.REMOVE)
    private List<TroubleImage> imageUrls;

    @OneToMany(mappedBy = "trouble", cascade = CascadeType.REMOVE)
    private List<TroubleTag> troubleTags;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Trouble(String title, String content, String question, String answer, Long viewCount, User user){
        this.title = title;
        this.content = content;
        this.question = question;
        this.answer = answer;
        this.viewCount = viewCount;
        this.user = user;
    }

    public void update(TroubleRequestDto troubleRequestDto) {
        this.title = troubleRequestDto.getTitle();
        this.content = troubleRequestDto.getContents();
        this.question = troubleRequestDto.getQuestion();
        this.answer = troubleRequestDto.getAnswer();
    }
}
