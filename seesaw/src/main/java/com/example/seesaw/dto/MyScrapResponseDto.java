package com.example.seesaw.dto;

import com.example.seesaw.model.Post;
import com.example.seesaw.model.PostImage;
import com.example.seesaw.model.PostScrap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyScrapResponseDto {
    private Long scrapId; // 스크랩 id
    private String title; // 제목
    private int views; // 조회수
    private int scrapCount; // 스크랩 횟수
    private int commentCount; // 댓글 개수
    private String mainImage; // 메인 이미지

    public MyScrapResponseDto(PostScrap postScrap, Post post, int scrapCount, PostImage postImage){
        this.scrapId = postScrap.getId();
        this.title = post.getTitle();
//        this.views = post.getViews();   // 아직 테이블에 없음
        this.scrapCount = scrapCount;
//        this.commentCount = post.getCommentCount(); // 아직 테이블
        this.mainImage = postImage.getImageUrl();
    }
}
