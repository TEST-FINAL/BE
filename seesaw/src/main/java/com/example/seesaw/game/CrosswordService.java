package com.example.seesaw.game;

import com.example.seesaw.dto.CrossWordResponseDto;
import com.example.seesaw.repository.CrosswordRepository;
import com.example.seesaw.repository.QuizNumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CrosswordService {

    private final CrosswordRepository crosswordRepository;
    private final QuizNumRepository quizNumRepository;

    /** the board of the current game **/
    private static Board board;

    public List<CrossWordResponseDto> getWord() {
        System.out.println("Welcome to Random Crossword Maker!\n");
        // 스타트 게시
        startGame(10);

        return board.printClues();
    }

    /** initialises and starts the game **/
    private void startGame(int dim) {
        // words 에는 정렬된 txt 파일들의 파일이 있다.
        String[] words = WordsGetter.getRandomWords(-1);
        board = new Board(dim, crosswordRepository, quizNumRepository);

        for (String word : words) {
            if (!board.allWordsPlaced()) {
                board.tryAddWord(word);
            } else {
                break;
            }
        }
    }
}
