package com.example.seesaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TroubleResponseDto {
    private String title;
    private String contents;
    private String question;
    private String answer;
    private List<String> tagName;
    private List<String> imageUrls;
}
