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

    private Long scrapId;           // 스크랩 id
    private String title;           // 제목
    private Long views;              // 조회수
    private Long scrapCount;         // 스크랩 횟수
    private Long commentCount;       // 댓글 개수
    private String mainImage;       // 메인 이미지

    public MyScrapResponseDto(PostScrap postScrap, Post post, Long scrapCount, Long commentCount, PostImage postImage){
        this.scrapId = postScrap.getId();
        this.title = post.getTitle();
        this.views = post.getViews();
        this.scrapCount = scrapCount;
        this.commentCount = commentCount;
        this.mainImage = postImage.getPostImage();
    }

}
