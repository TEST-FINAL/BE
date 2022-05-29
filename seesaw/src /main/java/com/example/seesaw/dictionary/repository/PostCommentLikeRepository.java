package com.example.seesaw.dictionary.repository;

import com.example.seesaw.dictionary.model.PostComment;
import com.example.seesaw.dictionary.model.PostCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Long> {

    PostCommentLike findByPostCommentAndUserId(PostComment postComment, Long userId);
}
