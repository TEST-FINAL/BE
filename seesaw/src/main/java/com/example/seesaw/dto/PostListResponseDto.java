package com.example.seesaw.dto;

import com.example.seesaw.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponseDto {

    private Long postId;
    private String title;
    private String contents;
    private String generation;
    private String postImages;
    private Long views;
    private Long scrapCount;
    private boolean scrapStatus;


    public PostListResponseDto(Post post, String postImages){
        this.postId = post.getId();
        this.title = post.getTitle();
        this.scrapCount = post.getScrapCount();
        this.contents = post.getContents();
        this.generation = post.getGeneration();
        this.views = post.getViews();
        this.postImages = postImages;
    }

    public PostListResponseDto(Post post, boolean scrapStatus, String postImage) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.scrapCount = post.getScrapCount();
        this.contents = post.getContents();
        this.generation = post.getGeneration();
        this.views = post.getViews();
        this.postImages = postImage;
        this.scrapStatus = scrapStatus;
    }
}
