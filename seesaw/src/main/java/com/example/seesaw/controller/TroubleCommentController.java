package com.example.seesaw.controller;


import com.example.seesaw.dto.TroubleCommentRequestDto;
import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.service.TroubleCommentLikeService;
import com.example.seesaw.service.TroubleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trouble/comment")
public class TroubleCommentController {

    private final TroubleCommentService troubleCommentService;
    private final TroubleCommentLikeService troubleCommentLikeService;

    //고민글 댓글 등록
    @PostMapping("/{troubleId}")
    public ResponseEntity<TroubleCommentRequestDto> registerComment(
            @PathVariable(name="troubleId") Long troubleId,
            @RequestBody TroubleCommentRequestDto troubleCommentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        TroubleCommentRequestDto troubleCommentDto = troubleCommentService.registerComment(troubleId, troubleCommentRequestDto, userDetails.getUser());

        return ResponseEntity.ok()
                .body(troubleCommentDto);
    }

    // 고민글 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<TroubleCommentRequestDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody TroubleCommentRequestDto troubleCommentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TroubleCommentRequestDto troubleCommentDto = troubleCommentService.updateComment(commentId, troubleCommentRequestDto, userDetails.getUser());

        return ResponseEntity.ok()
                .body(troubleCommentDto);
    }

    // 고민글 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<TroubleCommentRequestDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TroubleCommentRequestDto troubleCommentRequestDto = troubleCommentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok()
                .body(troubleCommentRequestDto);
    }

    // 고민 댓글 좋아요/취소
    @PostMapping("/{commentId}/like")
    public TroubleCommentRequestDto getGoods(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return troubleCommentLikeService.getTroubleCommentLikes(commentId, userDetails.getUser());
    }
}
