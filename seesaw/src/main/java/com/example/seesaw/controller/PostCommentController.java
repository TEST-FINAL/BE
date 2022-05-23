package com.example.seesaw.controller;


import com.example.seesaw.dto.PostCommentDto;
import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.service.PostCommentLikeService;
import com.example.seesaw.service.PostCommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PostCommentController {

    private final PostCommentService postCommentService;
    private final PostCommentLikeService postCommentLikeService;

    // 댓글 작성
    @PostMapping("/api/post/comment/{postId}")
    public ResponseEntity<PostCommentDto> registerPostComment(
            @PathVariable(name = "postId") Long postId,
            @RequestBody PostCommentDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostCommentDto postCommentDto = postCommentService.registerPostComment(postId, requestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body(postCommentDto);
    }

    // 댓글 수정
    @PutMapping("/api/post/comment/{commentId}")
    public ResponseEntity<PostCommentDto> updatePostComment(
            @PathVariable Long commentId,
            @RequestBody PostCommentDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostCommentDto postCommentDto = postCommentService.updatePostComment(commentId, requestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body(postCommentDto);
    }

    //댓글 삭제
    @DeleteMapping("/api/post/comment/{commentId}")
    public ResponseEntity<PostCommentDto> deletePostComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostCommentDto postCommentDto = postCommentService.deletePostComment(commentId, userDetails.getUser());
        return ResponseEntity.ok()
                .body(postCommentDto);
    }

    @ApiOperation("예시 좋아요/취소")
    @PostMapping("/api/post/comment/{commentId}/like")
    public PostCommentDto getGoods(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postCommentLikeService.getPostCommentLikes(commentId, userDetails.getUser());
    }
}




