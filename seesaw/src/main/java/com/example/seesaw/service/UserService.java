package com.example.seesaw.service;

import com.example.seesaw.dto.*;
import com.example.seesaw.exception.CustomException;
import com.example.seesaw.exception.ErrorCode;
import com.example.seesaw.model.User;
import com.example.seesaw.model.UserProfile;
import com.example.seesaw.model.UserProfileNum;
import com.example.seesaw.model.UserRoleEnum;
import com.example.seesaw.repository.MbtiRepository;
import com.example.seesaw.repository.UserProfileNumRepository;
import com.example.seesaw.repository.UserProfileRepository;
import com.example.seesaw.repository.UserRepository;
import com.example.seesaw.security.jwt.JwtDecoder;
import com.example.seesaw.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileNumRepository userProfileNumRepository;
    private final MbtiRepository mbtiRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtDecoder jwtDecoder;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public void registerUser(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        checkUserName(username);
        String generation = requestDto.getGeneration();
        MbtiRequestDto mbtiRequestDto = new MbtiRequestDto(requestDto.getEnergy(), requestDto.getInsight(), requestDto.getJudgement(), requestDto.getLifePattern());
        String mbti = checkMbti(mbtiRequestDto);
        String nickname = requestDto.getNickname();
        checkNickName(nickname);
        Long postCount = 0L;

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        //profile 저장
        List<Long> charIds = requestDto.getCharId();
        if (charIds == null){
            throw new IllegalArgumentException("charIds가 null 입니다.");
        }

        // 패스워드 암호화
        String enPassword = passwordEncoder.encode(requestDto.getPwd());
        User user = new User(username, enPassword, nickname, generation, postCount, mbti,role);
        userRepository.save(user); // DB 저장

        for(Long charId : charIds){
            UserProfile userProfile = userProfileRepository.findByCharId(charId);
            UserProfileNum userProfileNum = new UserProfileNum(userProfile, user);
            userProfileNumRepository.save(userProfileNum);
        }

    }

    // 아이디 유효성 검사
    private void checkUserName(String username) {
        Optional<User> foundByUserName = userRepository.findByUsername(username);
        if (foundByUserName.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME);
        }
        Pattern userNamePattern = Pattern.compile("\\w+@\\w+\\.\\w+(\\.\\w+)?");
        Matcher userNameMatcher = userNamePattern.matcher(username);
        if (username.length() == 0) {
            throw new CustomException(ErrorCode.BLANK_USER_NAME);
        }
        if (!userNameMatcher.matches()) {
            throw new CustomException(ErrorCode.INVALID_PATTERN_USER_NAME);
        }
    }


    //비밀번호 유효성 검사
    private void checkUserPw(String pwd, String pwdCheck) {
        Pattern userPwPattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$");
        Matcher userPwMatcher = userPwPattern.matcher(pwd);
        if (pwd.length() == 0) {
            throw new CustomException(ErrorCode.BLANK_USER_PW);
        }
        if (!userPwMatcher.matches()) {
            throw new CustomException(ErrorCode.INVALID_PATTERN_USER_PW);
        }
        // password 일치여부
        if (pwdCheck.length() == 0) {
            throw new CustomException(ErrorCode.BLANK_USER_PW_CHECK);
        }
        if (!pwd.equals(pwdCheck)) {
            throw new CustomException(ErrorCode.NOT_EQUAL_USER_PW_CHECK);
        }
    }

    //닉네임 유효성 검사
    public String checkNickName(String nickname) {
        Pattern nickNamePattern = Pattern.compile("^\\S{2,8}$");
        Matcher nickNameMatcher = nickNamePattern.matcher(nickname);

        Optional<User> foundByNickName = userRepository.findByNickname(nickname);
        if (foundByNickName.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NICKNAME);
        }
        if (nickname.length() == 0) {
            throw new CustomException(ErrorCode.BLANK_USER_NICKNAME);
        }
        if (!nickNameMatcher.matches()) {
            throw new CustomException(ErrorCode.INVALID_PATTERN_USER_NICKNAME);
        }
        return nickname;
    }

    public String validateToken(RefreshTokenDto refreshTokenDto) {
        String username = jwtDecoder.decodeUsername(refreshTokenDto.getRefreshToken());
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        return JwtTokenUtils.generateJwtToken(user);
    }

    public String checkMbti(MbtiRequestDto mbtiRequestDto) {
        String mbtiName = mbtiRequestDto.getEnergy() + mbtiRequestDto.getInsight() + mbtiRequestDto.getJudgement() + mbtiRequestDto.getLifePattern();
        if(mbtiName.length() != 4 || mbtiName.contains("null")){
            throw new CustomException(ErrorCode.BLANK_USER_MBTI);
        }
        //MBTI table 만들어서 맞는 설명으로 치환해서 내려주기
        String detail = mbtiRepository.findByMbtiName(mbtiName).getDetail();
        if(detail.isEmpty()){
            throw new IllegalArgumentException("해당하는 MBTI가 없습니다.");
        }
        return detail;
    }

    @Transactional
    public UserInfoResponseDto findUserInfo(User user) {

        List<UserProfileNum> userProfileNums = userProfileNumRepository.findAllByUserId(user.getId());
        if(userProfileNums.isEmpty()){
            throw new IllegalArgumentException("저장된 userProfileId 가 없습니다.");
        }

        List<ProfileListDto> profileListDtos = new ArrayList<>();
        for(UserProfileNum num : userProfileNums){
            UserProfile userProfile = userProfileRepository.findById(num.getUserProfile().getId()).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 userProfile 이 없습니다."));
            if (userProfile.getCategory().equals("faceUrl")){
                ProfileListDto faceUrl = new ProfileListDto(userProfile.getCharId(), userProfile.getImageUrl());
                profileListDtos.add(faceUrl);
            } else if(userProfile.getCategory().equals("accessoryUrl")){
                ProfileListDto accessoryUrl = new ProfileListDto(userProfile.getCharId(), userProfile.getImageUrl());
                profileListDtos.add(accessoryUrl);
            } else if(userProfile.getCategory().equals("backgroundUrl")){
                ProfileListDto backgroundUrl = new ProfileListDto(userProfile.getCharId(), userProfile.getImageUrl());
                profileListDtos.add(backgroundUrl);
            }
        }
        return new UserInfoResponseDto(user.getUsername(), user.getNickname(), profileListDtos);
    }

    public void checkUser(UserCheckRequestDto userCheckRequestDto) {
        String username = userCheckRequestDto.getUsername();
        String pwd = userCheckRequestDto.getPwd();
        String pwdCheck = userCheckRequestDto.getPwdCheck();

        //아이디 유효성 검사
        checkUserName(username);

        //비밀번호 유효성 검사
        checkUserPw(pwd, pwdCheck);
    }
}

