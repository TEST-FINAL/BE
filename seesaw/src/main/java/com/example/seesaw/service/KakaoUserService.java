package com.example.seesaw.service;

import com.example.seesaw.dto.KakaoGenerationDto;
import com.example.seesaw.dto.KakaoUserInfoDto;
import com.example.seesaw.model.User;
import com.example.seesaw.model.UserRoleEnum;
import com.example.seesaw.repository.UserRepository;
import com.example.seesaw.security.UserDetailsImpl;
import com.example.seesaw.security.UserDetailsServiceImpl;
import com.example.seesaw.security.jwt.JwtTokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class KakaoUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public List<String> kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        System.out.println("인가 코드 : " + code);
        System.out.println("엑세스 토큰 : " + accessToken);

        // 2. "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. 로그인 JWT 토큰 발행
        return jwtTokenCreate(kakaoUser);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        // 클라이언트 아이디, url 확인
        body.add("client_id", "6f05e336898a8b021c45ac7c1f8770b8");
        body.add("redirect_uri", "http://localhost:3000/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
//        String nickname = jsonNode.get("properties")
//                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        return new KakaoUserInfoDto(id, email);
    }

    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        String kakaoUsername = kakaoUserInfo.getEmail();

        System.out.println("input kakaoEmail : " +kakaoUsername);

        User kakaoUser = userRepository.findByUsername(kakaoUsername)
                .orElse(null);
        if (kakaoUser == null) {
            // 회원가입

            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);


            kakaoUser = new User(kakaoUsername, encodedPassword, 0L, UserRoleEnum.USER, kakaoId);
            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    //강제 로그인
    private List<String> jwtTokenCreate(User kakaoUser) {

        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl kakaoUserDetails = ((UserDetailsImpl) authentication.getPrincipal());

        System.out.println("kakaoUserDetails : " + kakaoUserDetails.toString());

        final String accessToken = JwtTokenUtils.generateJwtToken(kakaoUserDetails.getUser());
        final String refreshtoken = userDetailsServiceImpl.saveRefershToken(kakaoUserDetails.getUser());

        List<String> tokens = new ArrayList<>();
        tokens.add(accessToken);
        tokens.add(refreshtoken);

        return tokens;
    }

    public void userGeneration(KakaoGenerationDto kakaoGenerationDto, User user) {
        User kakaoUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 없습니다.")
        );
        System.out.println("세대저장 위해 찾은 kakaoUsername : " + kakaoUser.getUsername());
        kakaoUser.setGeneration(kakaoGenerationDto.getGeneration());
        userRepository.save(kakaoUser);
    }
}
