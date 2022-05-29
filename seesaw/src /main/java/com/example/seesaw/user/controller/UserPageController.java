package com.example.seesaw.user.controller;

import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.trouble.service.TroubleService;
import com.example.seesaw.user.dto.*;
import com.example.seesaw.user.service.UserPageService;
import com.example.seesaw.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserPageController {

    private final UserPageService userPageService;
    private final UserService userService;
    private final TroubleService troubleService;

    //내정보 조회
    @GetMapping("/api/mypage")
    public ResponseEntity<UserInfoResponseDto> findMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoResponseDto userInfoResponseDto = userService.findUserInfo(userDetails.getUser());
        return ResponseEntity.ok()
                .body(userInfoResponseDto);
    }

    //닉네임, 프로필 이미지 수정
    @PutMapping("/api/mypage/profile")
    public ResponseEntity<String> updateProfile(
            @RequestBody ProfileRequestDto profileRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userPageService.updateProfile(profileRequestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body("닉네임, 프로필 이미지 수정 완료");
    }

    // 내가 스크랩한 글 조회
    @GetMapping("/api/mypage/scraps")
    public ResponseEntity<List<MyScrapResponseDto>> getMyScrapPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<MyScrapResponseDto> myScrapResponseDtos = userPageService.getMyScraps(userDetails.getUser());

        return ResponseEntity.ok()
                .body(myScrapResponseDtos);
    }
    // 내가 등록한 단어 조회
    @GetMapping("/api/mypage/posts")
    public ResponseEntity<List<MyPostResponseDto>> getMyPostPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<MyPostResponseDto> myPostResponseDtos = userPageService.getMyPosts(userDetails.getUser());

        return ResponseEntity.ok()
                .body(myPostResponseDtos);
    }
    // 내가 등록한 고민글 조회
    @GetMapping("/api/mypage/troubles")
    public ResponseEntity<List<MyTroublesResponseDto>> getMyTroublePage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<MyTroublesResponseDto> myTroublesResponseDtos = userPageService.getMyTroubles(userDetails.getUser());

        return ResponseEntity.ok()
                .body(myTroublesResponseDtos);
    }
}
