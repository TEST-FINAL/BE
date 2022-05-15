package com.example.seesaw.repository;


import com.example.seesaw.model.Crossword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CrosswordRepository extends JpaRepository<Crossword, Long> {

    //게임용
    List<Crossword> findAllById(Long postId);

}
