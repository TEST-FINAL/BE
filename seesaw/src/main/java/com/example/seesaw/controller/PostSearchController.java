package com.example.seesaw.controller;

import com.example.seesaw.dto.PostSearchDto;
import com.example.seesaw.dto.PostSearchResponseDto;
import com.example.seesaw.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostSearchController {

    private final PostService postService;

    // 검색
    @GetMapping("/api/post/search")
    public ResponseEntity<PostSearchDto> search(@RequestParam(value = "keyword") String keyword) {
        PostSearchDto searchList = postService.searchPosts(keyword, keyword);
        return ResponseEntity.ok()
                .body(searchList);
    }
}
