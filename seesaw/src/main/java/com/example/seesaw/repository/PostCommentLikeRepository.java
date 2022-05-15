package com.example.seesaw.repository;

import com.example.seesaw.model.PostComment;
import com.example.seesaw.model.PostCommentLike;
import com.example.seesaw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Long> {

    PostCommentLike findByPostCommentAndUserId(PostComment postComment, Long userId);
}
