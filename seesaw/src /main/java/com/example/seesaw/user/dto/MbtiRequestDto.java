package com.example.seesaw.user.dto;

import com.example.seesaw.kakao.dto.KakaoRequstDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiRequestDto {

    private String id;
    private String energy;
    private String insight;
    private String judgement;
    private String lifePattern;

    public MbtiRequestDto(KakaoRequstDto kakaoRequstDto) {
        this.id = kakaoRequstDto.getKakaoId();
        this.energy = kakaoRequstDto.getEnergy();
        this.insight = kakaoRequstDto.getInsight();
        this.judgement = kakaoRequstDto.getJudgement();
        this.lifePattern = kakaoRequstDto.getLifePattern();
    }
}
