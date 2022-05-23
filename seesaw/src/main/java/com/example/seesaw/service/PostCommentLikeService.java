package com.example.seesaw.service;

import com.example.seesaw.dto.PostCommentDto;
import com.example.seesaw.model.PostComment;
import com.example.seesaw.model.PostCommentLike;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.PostCommentLikeRepository;
import com.example.seesaw.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostCommentLikeService {

    private final PostCommentRepository postCommentRepository;
    private final PostCommentLikeRepository postCommentLikeRepository;
    private final PostService postService;


    @Transactional
    public PostCommentDto getPostCommentLikes(Long commentId, User user) {
        Long userId= user.getId();
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );
        PostCommentLike savedLike = postCommentLikeRepository.findByPostCommentAndUserId(postComment, userId);

        if(savedLike != null){
            postCommentLikeRepository.deleteById(savedLike.getId());
            postComment.setLikeCount(postComment.getLikeCount()-1); //고민댓글 좋아요 수 -1
            postCommentRepository.save(postComment);

        } else{
            PostCommentLike postCommentLike = new PostCommentLike(postComment, user);
            postCommentLikeRepository.save(postCommentLike);
            postComment.setLikeCount(postComment.getLikeCount()+1); //고민댓글 좋아요 수 +1
            postCommentRepository.save(postComment);
        }
        return postService.getPostCommentDto(user, postComment);
    }

}