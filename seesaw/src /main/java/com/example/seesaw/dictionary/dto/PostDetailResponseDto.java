package com.example.seesaw.dictionary.dto;

import com.example.seesaw.user.dto.ProfileListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailResponseDto {

    private boolean scrapStatus;        // 스크랩 여부
    private String title;
    private String contents;
    private String videoUrl;
    private String nickname;            // 현재 로그인한 사용자 닉네임
    private String generation;
    private String lastNickname;        // 포스트 마지막 수정한 사용자 닉네임
    private String postUpdateTime;      // 포스트 등록 시간
    private Long views;
    private Long scrapCount;
    private Long commentCount;
    private List<String> tagNames;
    private List<String> postImages;
    private List<ProfileListDto> profileImages;
    private List<PostCommentDto> postComments;


    public PostDetailResponseDto(PostResponseDto responseDto) {
        this.tagNames = responseDto.getTagNames();
        this.postImages = responseDto.getPostImages();
    }

}
