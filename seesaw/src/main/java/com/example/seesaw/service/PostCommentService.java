package com.example.seesaw.service;

import com.example.seesaw.dto.PostCommentDto;
import com.example.seesaw.model.Post;
import com.example.seesaw.model.PostComment;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.PostCommentRepository;
import com.example.seesaw.repository.PostRepository;
import com.example.seesaw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    // 댓글 등록
    public PostCommentDto registerPostComment(Long postId, PostCommentDto requestDto, User user) {
        User commentUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalStateException("해당하는 USER 가 없습니다.")
        );
        requestDto.setNickname(commentUser.getNickname());
        Post savedPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시글이 없습니다."));
        PostComment postComment = new PostComment(savedPost, requestDto);
        postCommentRepository.save(postComment);
        // 댓글 response
        return postService.getPostCommentDto(user, postComment);
    }

    // 댓글 수정
    public PostCommentDto updatePostComment(Long commentId, PostCommentDto requestDto, User user) {
        PostComment postComment = checkCommentUser(commentId, user);
        postComment.setNickname(user.getNickname());
        postComment.setComment(requestDto.getComment());
        postCommentRepository.save(postComment);
        return postService.getPostCommentDto(user, postComment);
    }

    // 댓글 삭제
    public PostCommentDto deletePostComment(Long commentId, User user) {
        PostComment postComment = checkCommentUser(commentId, user);
        List<PostComment> postCommentList = postCommentRepository.findAllByPostIdOrderByCreatedAtDesc(postComment.getPost().getId());
        int index = postCommentList.indexOf(postComment); // index 0,1,2,3/4,5,6,7/8,9,10,11/12,13,14,15
        postCommentRepository.deleteById(commentId);
        int a = index / 4 +1;
        if(a == (postCommentList.size()-1)/4 +1){
            return new PostCommentDto(postCommentList.size()-1);
        }
        return postService.getPostCommentDto(user, postCommentList.get(a*4));
    }
    // 댓글 유저 확인
    public PostComment checkCommentUser(Long commentId, User user){
        User commentUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalStateException("해당하는 USER 가 없습니다.")
        );
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("해당 댓글이 없습니다."));
        if(!commentUser.getNickname().equals(postComment.getNickname())){
            throw new IllegalArgumentException("댓글 작성자가 아니므로 댓글 수정, 삭제가 불가합니다.");
        }
        return postComment;
    }

}
