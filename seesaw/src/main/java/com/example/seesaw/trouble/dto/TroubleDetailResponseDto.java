package com.example.seesaw.trouble.dto;

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
public class TroubleDetailResponseDto {

    private String nickname; // 본인 닉네임.
    private String writer; // 글작성자 이
    private String title;
    private String contents;
    private String question;
    private String answer;
    private String troubleTime;
    private Long views;
    private Long commentCount;
    private List<String> tagNames;          // tagName -> tagNames
    private List<String> troubleImages;     // imageUrls -> troubleImages
    private List<ProfileListDto> profileImages;
    private List<TroubleCommentRequestDto> troubleComments;

    public TroubleDetailResponseDto(TroubleDto troubleDto) {
        this.title = troubleDto.getTitle();
        this.contents = troubleDto.getContents();
        this.question = troubleDto.getQuestion();
        this.answer = troubleDto.getAnswer();
        this.tagNames = troubleDto.getTagNames();
        this.troubleImages = troubleDto.getTroubleImages();
    }
}
