package com.example.seesaw.dictionary.dto;

import com.example.seesaw.dictionary.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSearchResponseDto {

    private Long postId;
    private String title;
    private String contents;
    private String generation;
    private Long scrapCount;
    private Long views;
    private String postImage;
    private boolean scrapStatus;


    @Builder
    public PostSearchResponseDto(Long id, String title, String contents, String generation, Long views, Long scrapCount,String postImage ,boolean scrapStatus) {
        this.postId = id;
        this.title = title;
        this.contents = contents;
        this.generation = generation;
        this.views = views;
        this.scrapCount = scrapCount;
        this.postImage = postImage;
        this.scrapStatus = scrapStatus;
    }

    public Post toEntity() {
        Post build = Post.builder()
                .id(postId)
                .title(title)
                .contents(contents)
                .generation(generation)
                .build();
        return build;

    }

}
