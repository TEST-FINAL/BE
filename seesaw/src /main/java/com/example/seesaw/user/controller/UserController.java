package com.example.seesaw.user.controller;

import com.example.seesaw.user.dto.*;
import com.example.seesaw.user.service.UserPageService;
import com.example.seesaw.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserPageService userPageService;

    //이메일, 비밀번호 유효성 확인
    @PostMapping("/user/check")
    public ResponseEntity<String> checkUser(@RequestBody UserCheckRequestDto userCheckRequestDto) {
        String id = userService.checkUser(userCheckRequestDto);
        return ResponseEntity.ok()
                .body(id);
    }

    //mbti 유효성 확인 후 내용 Return
    @PostMapping("/user/mbti")
    public ResponseEntity<String> checkMbti(@RequestBody MbtiRequestDto mbtiRequestDto) {
        String mbtiDetail = userService.checkMbti(mbtiRequestDto);
        return ResponseEntity.ok()
                .body(mbtiDetail);
    }

    //회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return ResponseEntity.ok()
                .body("회원가입 완료");
    }

    //accessToken 만료 시 refreshToken 유효한지 확인 후 accessToken 재발급
    @PostMapping("/user/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        String accessToken = userService.refreshToken(refreshTokenDto);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .body("accessToken 재발급 완료");
    }

    //캐릭터 커스터마이징 사진조회
    @GetMapping("/user/profiles")
    public ProfileResponseDto findProfiles() {
        return userPageService.findProfiles();
    }

}

