package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TroubleAllResponseDto {

    private Long id;
    private String nickname;
    private String title;
    private String contents;
    private String question;
    private String answer;
    private String postTime;
    private Long views;
    private Long commentCount;
    private List<String> tagNames;
    private List<String> troubleImages;     // imageUrls -> troubleImages
    private List<ProfileListDto> profileImages;


    public TroubleAllResponseDto(TroubleDto troubleDto) {
        this.title = troubleDto.getTitle();
        this.contents = troubleDto.getContents();
        this.question = troubleDto.getQuestion();
        this.answer = troubleDto.getAnswer();
        this.tagNames = troubleDto.getTagNames();
        this.troubleImages = troubleDto.getTroubleImages();
    }
}
