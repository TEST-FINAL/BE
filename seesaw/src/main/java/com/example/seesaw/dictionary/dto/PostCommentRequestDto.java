package com.example.seesaw.dictionary.dto;

import com.example.seesaw.user.dto.ProfileListDto;
import com.example.seesaw.dictionary.model.PostComment;
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

    private Long commentId;
    private boolean commentLikeStatus;
    private String nickname;
    private String comment;
    private String commentTime;
    private Long commentLikeCount;
    List<ProfileListDto> ProfileImages;


    public PostCommentRequestDto(PostComment postComment) {
        this.commentId = postComment.getId();
        this.nickname = postComment.getNickname();
        this.comment = postComment.getComment();
    }

}
