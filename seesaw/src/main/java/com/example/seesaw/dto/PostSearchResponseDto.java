package com.example.seesaw.dto;

import com.example.seesaw.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSearchResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String generation;
    private Long imageCount;
    private Long commentCount;

    @Builder
    public PostSearchResponseDto(Long id, String title, String contents, String generation, Long imageCount, Long commentCount) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.generation = generation;
        this.imageCount = imageCount;
        this.commentCount = commentCount;
    }

    public Post toEntity() {
        Post build = Post.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .generation(generation)
                .build();
        return build;

    }

}
