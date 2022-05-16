package com.example.seesaw.model;

import com.example.seesaw.dto.PostCommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PostComment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String nickname;

    @Column
    private Long likeCount = 0L;


    public PostComment(Post post, PostCommentRequestDto requestDto) {
        this.post = post;
        this.comment = requestDto.getComment();
        this.nickname = requestDto.getNickname();
        this.likeCount = getLikeCount();
    }

}
