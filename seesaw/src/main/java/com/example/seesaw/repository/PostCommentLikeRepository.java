package com.example.seesaw.repository;

import com.example.seesaw.model.PostComment;
import com.example.seesaw.model.PostCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Long> {

    PostCommentLike findByPostCommentAndUserId(PostComment postComment, Long userId);
}
