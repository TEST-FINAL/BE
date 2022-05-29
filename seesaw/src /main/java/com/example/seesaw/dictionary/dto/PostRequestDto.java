package com.example.seesaw.dictionary.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String contents;
    private String videoUrl;
    private String generation;
    private List<String> tagNames;
    private List<String> postImages;

}
