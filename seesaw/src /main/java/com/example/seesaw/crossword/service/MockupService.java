package com.example.seesaw.crossword.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MockupService {

//    private final CrosswordRepository crosswordRepository;
//
//    // 목업용 게임판 quizId 1번 전체 조회
//    public List<CrossWordResponseDto> getWord() {
//        Long id = 1L;
//        QuizNum quizNum = new QuizNum(id); // 1로 고정
//        List<Crossword> crosswords = crosswordRepository.findAllByQuizNum(quizNum);
//        // 단어 시작 첫 좌표, 방향, 글자 수, 정답들 담을 리스트
//        List<CrossWordResponseDto> crossWordResponseDtos = new ArrayList<>();
//
//        for (Crossword c:crosswords
//             ) {
//            boolean pass = false;
//            crossWordResponseDtos.add(new CrossWordResponseDto(c, pass));
//        }
//
//        return crossWordResponseDtos;
//    }

}
