package com.example.seesaw.controller;


import com.example.seesaw.dto.TroubleCommentRequestDto;
import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.service.TroubleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trouble/comment")
public class TroubleCommentController {
    @Autowired
    private TroubleCommentService troubleCommentService;

    @PostMapping("/{troubleId}")
    public ResponseEntity<String> registerComment(
            @PathVariable(name="troubleId") Long troubleId,
            @RequestBody TroubleCommentRequestDto troubleCommentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        troubleCommentService.registerComment(troubleId, troubleCommentRequestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body("고민글 댓글등록 완료");
    }
    
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long commentId,
            @RequestBody TroubleCommentRequestDto troubleCommentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        troubleCommentService.updateComment(commentId, troubleCommentRequestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body("고민글 댓글수정 완료");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        troubleCommentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok()
                .body("고민글 댓글삭제 완료");
    }
}
