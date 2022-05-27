package com.example.seesaw.user.dto;

import com.example.seesaw.dictionary.model.Post;
import com.example.seesaw.dictionary.model.PostImage;
import com.example.seesaw.dictionary.model.PostScrap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyScrapResponseDto {
    private Long postId;
    private Long scrapId;           // 스크랩 id
    private String title;           // 제목
    private String contents;        //내용
    private String generation;      // 세대
    private Long views;              // 조회수
    private Long scrapCount;         // 스크랩 횟수
    private Long commentCount;       // 댓글 개수
    private String mainImage;       // 메인 이미지
    private boolean scrapStatus;    // 스크랩 했는지 안했는지 상태

    public MyScrapResponseDto(PostScrap postScrap, Post post, Long scrapCount, Long commentCount, PostImage postImage, boolean scrapStatus){
        this.postId = post.getId();
        this.scrapId = postScrap.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.generation = post.getGeneration();
        this.views = post.getViews();
        this.scrapCount = scrapCount;
        this.commentCount = commentCount;
        this.mainImage = postImage.getPostImage();
        this.scrapStatus = scrapStatus;
    }

}
