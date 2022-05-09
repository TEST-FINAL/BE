package com.example.seesaw.repository;

import com.example.seesaw.model.PostScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<PostScrap,Long> {
    List<PostScrap> findAllByUserId(Long userId);
    List<PostScrap> findAllByPostId(Long postId);
}
