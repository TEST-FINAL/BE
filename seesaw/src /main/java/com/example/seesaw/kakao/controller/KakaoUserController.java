package com.example.seesaw.kakao.controller;

import com.example.seesaw.kakao.dto.KakaoRequstDto;
import com.example.seesaw.kakao.dto.KakaoUserInfoDto;
import com.example.seesaw.kakao.service.KakaoUserService;
import com.example.seesaw.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class KakaoUserController {
    private final KakaoUserService kakaoUserService;
    private final UserService userService;
    public final String ACCESS_TOKEN = "Authorization";//"accessToken";
    public final String TOKEN_TYPE = "Bearer";

    @ApiOperation("카카오 로그인")
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<KakaoUserInfoDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        System.out.println("code : " + code);

        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        List<String> infos = kakaoUserService.kakaoLogin(code);

        System.out.println("kakao accesstoken : " + infos.get(2));
        System.out.println("kakao refreshtoken : " + infos.get(3));

        response.addHeader(ACCESS_TOKEN, TOKEN_TYPE + " " + infos.get(2) + ";" + TOKEN_TYPE + " " + infos.get(3));

        return ResponseEntity.ok()
                .body(new KakaoUserInfoDto(infos.get(0), infos.get(1)));

    }

    //mbti 유효성 확인 후 내용 Return
//    @PostMapping("/user/kakao/mbti")
//    public ResponseEntity<String> checkMbti(@RequestBody KakaoMbtiRequestDto kakoMbtiRequestDto) {
//        String mbtiDetail = kakaoUserService.checkKakaoMbti(kakoMbtiRequestDto);
//        return ResponseEntity.ok()
//                .body(mbtiDetail);
//    }

    @PostMapping("/user/kakao/signup")
    public ResponseEntity<String> kakaoUser(@RequestBody KakaoRequstDto kakaoRequstDto, HttpServletResponse response) throws JsonProcessingException {

        List<String> tokens = kakaoUserService.signUpUser(kakaoRequstDto);

        response.addHeader(ACCESS_TOKEN, TOKEN_TYPE + " " + tokens.get(0) + ";" + TOKEN_TYPE + " " + tokens.get(1));

        return ResponseEntity.ok()
                .body("카카오유저 회원가입 완료");
    }
}