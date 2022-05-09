package com.example.seesaw.controller;


import com.example.seesaw.dto.PostRequestDto;
import com.example.seesaw.dto.PostResponseDto;
import com.example.seesaw.model.Post;
import com.example.seesaw.repository.PostRepository;
import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;


    //단어 등록
    @PostMapping(value = "/api/post", headers = ("content-type=multipart/*"))
    public ResponseEntity<String> createPost(
            @RequestPart("com") PostRequestDto requestDto,
            @RequestPart("files") ArrayList<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.createPost(requestDto, files, userDetails.getUser());
        return ResponseEntity.ok()
                .body("단어장 등록완료.");
    }


    // 단어 중복 확인
    @GetMapping("/api/post/{title}/present")
    public ResponseEntity<Boolean> wordCheck(@PathVariable String title) {
        return ResponseEntity.ok(postService.wordCheck(title));
    }

    //단어 수정!
    @PutMapping("/api/post/{postId}/update")
    public ResponseEntity<String> updatePost(
            @PathVariable Long postId,
            @RequestPart(value = "postRequestDto") PostRequestDto requestDto,
            @RequestPart(value = "postResponseDto") PostResponseDto responseDto,
            @RequestPart(value = "file", required = false) ArrayList<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.updatePost(postId, requestDto, responseDto, files, userDetails.getUser());
        return ResponseEntity.ok()
                .body("단어장 수정완료.");
    }

    //단어장 삭제
    @DeleteMapping("api/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postRepository.deleteById(postId);
        return ResponseEntity.ok()
                .body("고민글 삭제완료");
    }











    // 검색
    @GetMapping("/api/post/search")
    public List<Post> search(String keyword) {
        return postRepository.findByTitleContainingOrContentsContaining(keyword, keyword);
    }

}
