package com.example.seesaw.controller;


import com.example.seesaw.dto.PostDetailResponseDto;
import com.example.seesaw.dto.PostRequestDto;
import com.example.seesaw.dto.PostScrapSortResponseDto;
import com.example.seesaw.dto.TroubleDetailResponseDto;
import com.example.seesaw.dto.*;
import com.example.seesaw.repository.PostRepository;
import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.service.PostScrapService;
import com.example.seesaw.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final PostScrapService postScrapService;


    //단어 등록
    @PostMapping(value = "/api/post", headers = ("content-type=multipart/*"))
    public ResponseEntity<String> createPost(
            @RequestPart("postRequestDto") PostRequestDto requestDto,
            @RequestPart(value = "files", required = false) ArrayList<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.createPost(requestDto, files, userDetails.getUser());
        return ResponseEntity.ok()
                .body("단어장 등록완료.");
    }

    // 단어 중복 확인
    @PostMapping("/api/post/{title}/present")
    public ResponseEntity<Boolean> wordCheck(@PathVariable String title) {
        return ResponseEntity.ok(postService.wordCheck(title));
    }

    //단어 수정!
    @PutMapping("/api/post/{postId}/update")
    public ResponseEntity<String> updatePost(
            @PathVariable Long postId,
            @RequestPart(value = "postRequestDto") PostRequestDto requestDto,
            @RequestPart(value = "files", required = false) ArrayList<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.updatePost(postId, requestDto, files, userDetails.getUser());
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

    //단어장 상세조회
    @GetMapping("api/post/{postId}/detail")
    public ResponseEntity<PostDetailResponseDto> findDetailPost(
            @RequestParam(value = "page") int page,
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        PostDetailResponseDto postDetailResponseDto = postService.findDetailPost(postId, page, userDetails.getUser());
        return ResponseEntity.ok()
                .body(postDetailResponseDto);
    }

    //단어장 스크랩, 스크랩취소
    @ApiOperation("단어장 스크랩")
    @PostMapping("/api/post/{postId}/scrap")
    public ResponseEntity<Boolean> scrapPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean sracpStatus = postScrapService.scrapPost(postId, userDetails.getUser());
        return ResponseEntity.ok()
                .body(sracpStatus);
    }

    // 사전 리스트 전체 조회(리스트 페이지)
    @GetMapping("/api/post/list")
    public ResponseEntity<List<PostListResponseDto>> getListPosts(){
        List<PostListResponseDto> postListResponseDtos = postService.findListPosts();

        return ResponseEntity.ok()
                .body(postListResponseDtos);
    }

    // 사전 글 스크랩순으로 16개 조회 (메인페이지)
    @GetMapping("/api/main/post/scrap")
    public ResponseEntity<List<PostScrapSortResponseDto>> getPosts(){
        List<PostScrapSortResponseDto> postAllResponseDtos = postService.findAllPosts();

        return ResponseEntity.ok()
                .body(postAllResponseDtos);
    }

    // 사전 글 렘덤 2개 조회 (메인페이지)
    @GetMapping("/api/main/post/random")
    public ResponseEntity<List<PostScrapSortResponseDto>> getRandomPosts(){
        List<PostScrapSortResponseDto> randomPostsResponseDtos = postService.findRandomPosts();
        return ResponseEntity.ok()
                .body(randomPostsResponseDtos);
    }

    // 사전 글 스크랩순으로 9개 조회 (메인페이지)
    @GetMapping("/api/main/postList")
    public ResponseEntity<List<PostListResponseDto>> getMainListPosts(){
        List<PostListResponseDto> postListResponseDtos = postService.findMainListPosts();

        return ResponseEntity.ok()
                .body(postListResponseDtos);
    }
}
