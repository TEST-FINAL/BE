package com.example.seesaw.dto;

import com.example.seesaw.model.PostComment;
import com.example.seesaw.model.TroubleComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostCommentRequestDto {

    private boolean commentLikeStatus;
    private String nickname;
    private String comment;
    private String commentTime;
    private Long CommentLikeCount;
    List<ProfileListDto> ProfileImages;


    public PostCommentRequestDto(PostComment postComment) {
        this.nickname = postComment.getNickname();
        this.comment = postComment.getComment();
    }

}
