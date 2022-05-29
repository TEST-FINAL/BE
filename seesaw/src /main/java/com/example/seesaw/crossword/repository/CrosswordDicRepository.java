package com.example.seesaw.crossword.repository;


import com.example.seesaw.crossword.model.CrosswordDic;
import com.example.seesaw.dictionary.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CrosswordDicRepository extends JpaRepository<CrosswordDic, Long> {

    //게임용
    List<CrosswordDic> findAllById(Long postId);

    List<CrosswordDic> findAllByTitleContaining(String oneWord);

    List<Post> findByTitleContainingOrContentsContaining(@Param("title") String title, @Param("contents") String contents);

    CrosswordDic findByTitle(String word);

}
