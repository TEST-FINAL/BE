package com.example.seesaw.dictionary.repository;

import com.example.seesaw.dictionary.model.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findAllByNickname(String nickname);

    List<PostComment> findAllByPostId(Long id);

    Page<PostComment> findAllByPostIdOrderByCreatedAtDesc(Long id, Pageable pageable);

    long countByPostId(Long id);

    List<PostComment> findAllByPostIdOrderByCreatedAtDesc(Long id);
}
