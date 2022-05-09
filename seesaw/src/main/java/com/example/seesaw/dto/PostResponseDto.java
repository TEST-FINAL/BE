package com.example.seesaw.dto;

import com.example.seesaw.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String contents;
    private List<String> imageList;
    private List<String> tagNames;
    private String videoUrl;

    public PostResponseDto(Post post, List<String> imageList, List<String> tagNames) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.imageList = imageList;
        this.videoUrl = post.getVideoUrl();
        this.tagNames = tagNames;
    }
}
