package com.example.seesaw.user.dto;

import com.example.seesaw.trouble.model.Trouble;
import com.example.seesaw.trouble.model.TroubleImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyTroublesResponseDto {

    private Long troubleId; // 고민 Id
    private String title; // 제목
    private String contents; // 내용
    private String question; // 질문자
    private String answer; // 답변자
    private Long views; // 조회수
    private Long commentCount; // 댓글 수
    private String mainImage; // 메인이미지

    public MyTroublesResponseDto(Trouble trouble,Long commentCount, TroubleImage troubleImage){
        this.troubleId = trouble.getId();
        this.title = trouble.getTitle();
        this.contents = trouble.getContents();
        this.question = trouble.getQuestion();
        this.answer = trouble.getAnswer();
        this.views = trouble.getViews();
        this.commentCount = commentCount;
        this.mainImage = troubleImage.getTroubleImage();
    }

}
