package com.example.seesaw.dto;

import com.example.seesaw.model.Trouble;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyTroublesResponseDto {
    private Long troubleId;
    private String title;

    public MyTroublesResponseDto(Trouble trouble){
        this.troubleId = trouble.getId();
        this.title = trouble.getTitle();
    }
}
