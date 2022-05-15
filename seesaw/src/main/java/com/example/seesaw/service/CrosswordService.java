package com.example.seesaw.service;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.model.Crossword;
import com.example.seesaw.repository.CrosswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CrosswordService {

    private final CrosswordRepository crosswordRepository;

    public List<CrossWordResponseDto> getWord(){
        Random random = new Random(); // 랜덤 객체 생성
        Random random1 = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)
        random1.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)
        // (0+1~299+1) 1부터 300까지 postId 난수 생성
        Long postId = (long) (random.nextInt(300) + 1); // 1부터 300 까지
        //(x,y) = (1,3)부터 시작
        int x = 1; int y = 3;
        //findById 는 optional 로 가져와야합니다.
        List<Crossword> crosswords = crosswordRepository.findAllById(postId);

        // 가로인지 세로인지 방향 난수로 정하기  (가로가 0 세로가 1)
        int direction = random.nextInt(2); // 0부터 1 까지 랜덤으로
        System.out.println(direction);
        // 가로일 때
        List<CrossWordResponseDto> crossWordResponseDtos = new ArrayList<>();
        for (Crossword crossword : crosswords){
            if(direction == 0){
                String[] str = crossword.getTitle().split(""); // [어, 쩔, 티, 비]
                for (String s : str) {
                    String c = crossword.getContents();
                    crossWordResponseDtos.add(new CrossWordResponseDto(x, y, s, c));  // 가로로 (1,3,어)(2,3,쩔)(3,3,티)(4,3,비)
                    x++;
                }
            }
            // 세로일 때
            else {
                String[] str = crossword.getTitle().split(""); // [어, 쩔, 티, 비]
                for (String s : str) {
                    String c = crossword.getContents();
                    crossWordResponseDtos.add(new CrossWordResponseDto(x, y, s, c));  // 가로로 (1,3,어)(2,3,쩔)(3,3,티)(4,3,비)
                    y++;
                }
            }
        }

        return crossWordResponseDtos;
    }
}
