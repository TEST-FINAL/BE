package com.example.seesaw.dictionary.repository;

import com.example.seesaw.dictionary.model.Post;
import com.example.seesaw.dictionary.model.PostScrap;
import com.example.seesaw.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostScrapRepository extends JpaRepository<PostScrap, Long> {

    PostScrap findByUserAndPost(User user, Post post);

    List<PostScrap> findAllByUserId(Long userId);

    long countByPostId(Long id);

    List<PostScrap> findAllByPostId(Long id);
}
