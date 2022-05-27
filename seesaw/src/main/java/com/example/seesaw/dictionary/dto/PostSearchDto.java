package com.example.seesaw.dictionary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostSearchDto {

    private Long listCount;
    private List<PostSearchResponseDto> postSearchList;

}
