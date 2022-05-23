package com.example.seesaw.service;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.model.Crossword;
import com.example.seesaw.model.CrosswordDic;
import com.example.seesaw.model.QuizNum;
import com.example.seesaw.repository.CrosswordDicRepository;
import com.example.seesaw.repository.CrosswordRepository;
import com.example.seesaw.repository.QuizNumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CrosswordService {

    private final CrosswordDicRepository crosswordDicRepository;
    private final CrosswordRepository crosswordRepository;
    private final QuizNumRepository quizNumRepository;
    // QuizNum 에 담을용 (autoWord 재귀 메서드 호출로 인해 list에 값이 들어감)
    List<Crossword> crosswords = new ArrayList<>();

    // 단어 시작 첫 좌표, 방향, 글자 수, 정답들 담을 리스트
    List<CrossWordResponseDto> crossWordResponseDtos = new ArrayList<>();

    public List<CrossWordResponseDto> getWord() {
        // 전체 quizNum
        List<QuizNum> quizNums = quizNumRepository.findAll();
        Long quizNumber = quizNums.get(quizNums.size()-1).getId() + 1;

        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)
        // (0+1~299+1) 1부터 300까지 postId 난수 생성 (전체 등록되어있는 단어 수를 bound 로 잡으면 된다.)
        Long postId = (long) (random.nextInt(350) + 1); // 1부터 300 까지
        //(x,y) = (1,1)부터 시작
        int x = 2;
        int y = 2;
        //findById 는 optional 로 가져와야합니다.  첫 단어만 메서드 밖에 생성
        List<CrosswordDic> crosswordDics = crosswordDicRepository.findAllById(postId);

        // 가로인지 세로인지 방향 난수로 정하기  (가로가 true 세로가 false)
        boolean isOriental = random.nextBoolean(); // true false 랜덤으로 선택

        System.out.println(isOriental); // 가로세로 방향 잘 정해졌는지 체크

        String[] firstWord = crosswordDics.get(0).getTitle().split("");

        Crossword crossword = new Crossword(x,y,crosswordDics.get(0).getTitle(),crosswordDics.get(0).getContents() ,firstWord.length,isOriental);
        // 첫번째 단어 넣기
        crosswordRepository.save(crossword); // 먼저 저장
        crossWordResponseDtos.add(new CrossWordResponseDto(crossword, false));
        crosswords.add(crossword);
        // 메서드 호출 (시작 x좌표, 시작 y좌표, 첫단어, 첫 방향)
        //autoWord(x, y, crosswordDics.get(0).getTitle(), isOriental);

        QuizNum quizNum = new QuizNum(quizNumber, crosswords);
//        quizNumRepository.save(quizNum);

        // 메서드 호출 (시작 x좌표, 시작 y좌표, 첫단어, 첫 방향)
        return crossWordResponseDtos;
    }

    // 자동으로 생성할 단어 메소드
    public List<CrossWordResponseDto> autoWord(int x, int y, String word, boolean isOriental) {
        // 바로 전 단어 기억하기
        String exWord = word;
        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis()); //난수가 규칙적으로 나오도록 설정(같은 수가 동시에 나오지 않도록)

        // 들어오는 단어 글자 수 파악하고 다음 단어 찾기
        String[] str = word.split("");
        int wordCount = str.length; // 글자 수
        // 마지막 글자가 포함되어있는 단어를 DB에서 찾는다.
        List<CrosswordDic> crosswordDicList = crosswordDicRepository.findAllByTitleContaining(str[wordCount-1]);
        int index = random.nextInt(crosswordDicList.size()); // 랜덤값 저장.
        // 종료 조건
        if(index == 0){
            return crossWordResponseDtos;
        }

        // 단어를 찾았다.
        CrosswordDic crosswordDic = crosswordDicList.get(index);
        String crossword = crosswordDic.getTitle();
        System.out.println("여기서부턴 두번째 생성단어 : "+ crossword);

        // 전 단어와 생성된 단어가 어디에서 만나는지
        String[] exWordStr = exWord.split(""); // 전단어 쪼개고

        // 가로일때
        if (isOriental) {
            x += wordCount;
            isOriental = false;
        } else { // 세로일때
            y += wordCount;
            isOriental = true;
        }

        Crossword crossword1 = new Crossword(x,y,crossword,crosswordDic.getContents(),wordCount,isOriental);
//        crosswordRepository.save(crossword1);
        crosswords.add(crossword1);
        crossWordResponseDtos.add(new CrossWordResponseDto(crossword1, false));
        // 다음 단어 찾으러 출발.
        autoWord(x, y, crossword, isOriental);

        return crossWordResponseDtos;
    }
}
