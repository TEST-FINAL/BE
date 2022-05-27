package com.example.seesaw.dictionary.repository;

import com.example.seesaw.dictionary.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    @Transactional
    void deleteAllByPostId(Long postId);

    List<PostTag> findAllByPostId(Long postId);

}
