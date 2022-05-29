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

public class PostCommentDto {
    private Long commentId;
    private String nickname;
    private String comment;
    private String commentTime;
    private Long CommentLikeCount;
    private Long CommentCount;
    List<ProfileListDto> ProfileImages;
    private boolean commentLikeStatus;

    public PostCommentDto(PostComment postComment) {
        this.commentId = postComment.getId();
        this.nickname = postComment.getNickname();
        this.comment = postComment.getComment();
    }

    public PostCommentDto(int commentCount) {
        this.CommentCount = (long) commentCount;
    }
}
