package com.example.seesaw.user.dto;

import com.example.seesaw.trouble.model.Trouble;
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
