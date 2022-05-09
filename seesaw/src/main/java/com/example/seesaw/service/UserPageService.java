package com.example.seesaw.service;

import com.example.seesaw.dto.*;
import com.example.seesaw.model.*;
import com.example.seesaw.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPageService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileNumRepository userProfileNumRepository;
    private final ScrapRepository scrapRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final TroubleRepository troubleRepository;

    //    public void checkProfile(ProfileRequestDto profileRequestDto) {
//        //닉네임 유효성 검사
//        checkNickName(profileRequestDto.getNickname());
//        //IDs 유효성 검사
//        List<Long> profileImageNums = profileRequestDto.getProfileImages();
//        for(Long num : profileImageNums){
//            userProfileRepository.findById(num).orElseThrow(
//                    () -> new IllegalArgumentException("해당하는 이미지가 없습니다.")
//            );
//            //UserProfileNum userProfileNum = new UserProfileNum(userProfile, user);
//            //userProfileNumRepository.save(userProfileNum);
//        }
//    }
    // 프로필 수정
    public void updateProfile(ProfileRequestDto profileRequestDto, User user) {
        //닉네임 유효성 검사 후 저장
        String nickname = userService.checkNickName(profileRequestDto.getNickname());
        user.setNickname(nickname);
        userRepository.save(user);
        //IDs 유효성 검사 후 IDs 저장
        List<Long> profileImageCharIds = profileRequestDto.getProfileImages();
        List<UserProfileNum> userProfileNums = new ArrayList<>();
        for (Long charId : profileImageCharIds) {
            UserProfile userProfile = userProfileRepository.findByCharId(charId);
            UserProfileNum userProfileNum = new UserProfileNum(userProfile, user);
            userProfileNums.add(userProfileNum);
        }
        userProfileNumRepository.deleteAllByUserId(user.getId());
        userProfileNumRepository.saveAll(userProfileNums);
    }

    public ProfileResponseDto findProfiles() {
        List<ProfileListDto> faceUrl = new ArrayList<>();
        List<ProfileListDto> accessoryUrl = new ArrayList<>();
        List<ProfileListDto> backgroudUrl = new ArrayList<>();
        List<UserProfile> userProfile = userProfileRepository.findAll();
        for (UserProfile profile : userProfile) {
            if (profile.getCategory().equals("faceUrl")) {
                faceUrl.add(new ProfileListDto(profile.getCharId(), profile.getImageUrl()));
            } else if (profile.getCategory().equals("accessoryUrl")) {
                accessoryUrl.add(new ProfileListDto(profile.getCharId(), profile.getImageUrl()));
            } else if (profile.getCategory().equals("backgroundUrl")) {
                backgroudUrl.add(new ProfileListDto(profile.getCharId(), profile.getImageUrl()));
            }
        }
        return new ProfileResponseDto(faceUrl, accessoryUrl, backgroudUrl);
    }

    // 내가 스크랩한 게시물 조회 (마이페이지)
    public List<MyScrapResponseDto> getMyScraps(User user) {
        List<PostScrap> postScraps = scrapRepository.findAllByUserId(user.getId());
        List<Post> posts = postRepository.findAll();

        List<MyScrapResponseDto> myScrapResponseDtos = new ArrayList<>();

        for (PostScrap postScrap : postScraps) {
            for (Post post : posts) {
                // postScrap테이블 postId 와 post테이불 postId가 일치할때
                if (post.getId().equals(postScrap.getPost().getId())){
                    // postId에 해당하는 postImage 가져오기
                    List<PostImage> postImages = postImageRepository.findAllByPostId(post.getId());
                    // 첫번째 이미지만 가져오기
                    PostImage postImage = postImages.get(0);
                    // 일치한 postId에 해당하는 scrap 횟수만 가져오기
                    List<PostScrap> scraps = scrapRepository.findAllByPostId(post.getId());
                    myScrapResponseDtos.add(new MyScrapResponseDto(postScrap, post, scraps.size(), postImage));
                }
            }
        }
        return myScrapResponseDtos;
    }

    // 내가 등록한 고민글 조회 (마이페이지)
    public List<MyTroublesResponseDto> getMyTroubles(User user){
        List<Trouble> troubles = troubleRepository.findAllByUserId(user.getId());

        List<MyTroublesResponseDto> myTroublesResponseDtos = new ArrayList<>();
        for (Trouble trouble: troubles){
            myTroublesResponseDtos.add(new MyTroublesResponseDto(trouble));
        }

        return myTroublesResponseDtos;
    }
}


