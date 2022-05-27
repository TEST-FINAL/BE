package com.example.seesaw.trouble.model;


import com.example.seesaw.trouble.dto.TroubleCommentRequestDto;
import com.example.seesaw.dictionary.model.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TroubleComment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Trouble trouble;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String nickname;

    @Column
    private Long likeCount = 0L;


    public TroubleComment(Trouble trouble, TroubleCommentRequestDto troubleCommentRequestDto) {
        this.trouble = trouble;
        this.comment = troubleCommentRequestDto.getComment();
        this.nickname = troubleCommentRequestDto.getNickname();
        this.likeCount = troubleCommentRequestDto.getCommentLikeCount();
    }


}
