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
public class PostScrapSortResponseDto {

    private Long postId;
    private String title;
    private String contents;
    private String postImage;
    private String generation;
    private Long scrapCount;

    public PostScrapSortResponseDto(Post post, String postImage){
        this.postId = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.postImage = postImage;
        this.generation = post.getGeneration();
        this.scrapCount = post.getScrapCount();
    }

}
