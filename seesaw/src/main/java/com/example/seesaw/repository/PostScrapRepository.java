package com.example.seesaw.repository;

import com.example.seesaw.model.Post;
import com.example.seesaw.model.PostScrap;
import com.example.seesaw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostScrapRepository extends JpaRepository<PostScrap, Long> {

    PostScrap findByUserAndPost(User user, Post post);

    List<PostScrap> findAllByUserId(Long userId);

    long countByPostId(Long id);

    List<PostScrap> findAllByPostId(Long id);
}
