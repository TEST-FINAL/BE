package com.example.seesaw.dto;

import com.example.seesaw.model.Trouble;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTroubleResponseDto {

    private Long troubleCount;
    private List<Trouble> troubleList;

}
