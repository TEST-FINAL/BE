package com.example.seesaw.crossword.repository;

import com.example.seesaw.crossword.model.Crossword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrosswordRepository extends JpaRepository<Crossword, Long> {
    //목업용 (지울예정)
//    List<Crossword> findAllByQuizNum(QuizNum quizNum);
//
    List<Crossword> findAllByQuizNumId(Long quizId);
}
