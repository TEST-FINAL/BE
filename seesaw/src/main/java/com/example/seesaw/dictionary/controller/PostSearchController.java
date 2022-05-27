package com.example.seesaw.dictionary.controller;

import com.example.seesaw.dictionary.dto.PostSearchDto;
import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.dictionary.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PostSearchController {

    private final PostService postService;

    // 검색
    @GetMapping("/api/post/search")
    public ResponseEntity<PostSearchDto> search(@RequestParam(value = "keyword") String keyword, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostSearchDto searchList = postService.searchPosts(keyword, keyword, userDetails.getUser());
        return ResponseEntity.ok()
                .body(searchList);
    }
}
