package com.example.seesaw.controller;


import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.service.TroubleLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class TroubleLikeController {

    private final TroubleLikeService troubleLikeService;

    @PostMapping("api/trouble/{commentId}/like")
    public boolean getGoods(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return troubleLikeService.getLikes(commentId, userDetails.getUser());
    }
}
