package com.example.seesaw.service;

import com.example.seesaw.dto.TroubleRequestDto;
import com.example.seesaw.dto.TroubleResponseDto;
import com.example.seesaw.dto.UserTroubleResponseDto;
import com.example.seesaw.model.Trouble;
import com.example.seesaw.model.TroubleImage;
import com.example.seesaw.model.TroubleTag;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.TroubleImageRepository;
import com.example.seesaw.repository.TroubleRepository;
import com.example.seesaw.repository.TroubleTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TroubleService {

    private final TroubleTagRepository troubleTagRepository;
    private final TroubleRepository troubleRepository;
    private final TroubleImageRepository troubleImageRepository;
    private final TroubleS3Service troubleS3Service;


//    @OverrIde
//    public String toString(){
//        return name;
//    }

    //Trouble 글 등록
    public void registerTrouble(TroubleRequestDto troubleRequestDto, List<MultipartFile> files, User user) {
        checkTrouble(troubleRequestDto);
        String name = null;
        for(MultipartFile file:files){
            name = file.getOriginalFilename();
            System.out.println("file이름은~~~:" + name);
        }

        List<String> imagePaths = new ArrayList<>();
        if (name.equals("")) {
            imagePaths.add("기본이미지 AWS에 저장해서 주소넣기!");
        } else {
            imagePaths.addAll(troubleS3Service.upload(files));
        }
        Trouble trouble = new Trouble(
                troubleRequestDto.getTitle(),
                troubleRequestDto.getContents(),
                troubleRequestDto.getQuestion(),
                troubleRequestDto.getAnswer(),
                0L,
                user
        );
        troubleRepository.save(trouble);

        for (String imagePath : imagePaths) {
            TroubleImage troubleImage = new TroubleImage(imagePath, user, trouble);
            troubleImageRepository.save(troubleImage);
        }

        List<String> tags = troubleRequestDto.getTagName();
        for (String tag : tags) {
            if(!tag.equals("")){
                TroubleTag troubleTag = new TroubleTag(tag, user, trouble);
                troubleTagRepository.save(troubleTag);
            }
        }
    }

    //고민글 수정 시 정보조회
    public TroubleResponseDto findTrouble(Long troubleId) {
        Trouble trouble = troubleRepository.findById(troubleId).orElseThrow(
                () -> new IllegalArgumentException("고민 Id에 해당하는 글이 없습니다.")
        );


        List<TroubleTag> troubleTags = troubleTagRepository.findAllByTroubleId(troubleId);
        if (troubleTags.isEmpty()){
            throw new IllegalArgumentException("고민 Id에 해당하는 테그가 없습니다.");
        }
        List<String> gomiTagList = new ArrayList<>();
        for(TroubleTag troubleTag : troubleTags){
            gomiTagList.add(troubleTag.getTagName());
        }

        List<TroubleImage> troubleImages = troubleImageRepository.findAllByTroubleId(troubleId);
        if (troubleImages.isEmpty()){
            throw new IllegalArgumentException("고민 Id에 해당하는 이미지가 없습니다.");
        }
        List<String> troubleImageList = new ArrayList<>();
        for(TroubleImage troubleImage : troubleImages){
            troubleImageList.add(troubleImage.getTroubleImageUrl());
        }

        return new TroubleResponseDto(trouble.getTitle(), trouble.getContent(), trouble.getQuestion(), trouble.getAnswer(), gomiTagList, troubleImageList);
    }

    //고민글 수정
    public void updateTrouble(TroubleRequestDto troubleRequestDto, List<MultipartFile> files, Long troubleId, User user) {
        Trouble trouble = troubleRepository.findById(troubleId).orElseThrow(
                () -> new IllegalArgumentException("고민 Id에 해당하는 고민글이 없습니다.")
        );
        //고민글 작성자 검사
        Long troubleUserId = trouble.getUser().getId();
        if(!user.getId().equals(troubleUserId)){
            throw new IllegalArgumentException("작성자가 아니므로 고민글 수정이 불가합니다.");
        }
        checkTrouble(troubleRequestDto);
        String name = null;
        for(MultipartFile file:files){
            name = file.getOriginalFilename();
            System.out.println("file이름은~~~:" + name);
        }
        trouble.update(troubleRequestDto);

        List<String> imagePaths = new ArrayList<>();
        if (name.equals("") && troubleRequestDto.getImageUrls().get(0).isEmpty()) {
            imagePaths.add("기본이미지 AWS에 저장해서 주소넣기!");
            troubleImageRepository.deleteAllByTroubleId(troubleId);
        } else if(!name.equals("")) {
            imagePaths.addAll(troubleS3Service.update(troubleId, troubleRequestDto.getImageUrls(), files));
        } else{
            imagePaths = troubleRequestDto.getImageUrls();
            troubleImageRepository.deleteAllByTroubleId(troubleId);
        }

        for (String imagePath : imagePaths) {
            TroubleImage troubleImage = new TroubleImage(imagePath, user, trouble);
            troubleImageRepository.save(troubleImage);
        }
        List<String> tags = troubleRequestDto.getTagName();
        troubleTagRepository.deleteAllByTroubleId(troubleId);
        for (String tag : tags) {
            if(!tag.equals("")){
                TroubleTag troubleTag = new TroubleTag(tag, user, trouble);
                troubleTagRepository.save(troubleTag);
            }
        }
    }

    //USER가 작성한 고민리스트 찾기
    public UserTroubleResponseDto findTroubles(User user) {
        List<Trouble> troubles = troubleRepository.findAllByUserId(user.getId());
        return new UserTroubleResponseDto((long) troubles.size(), troubles);
    }

    //고민글 유효성 검사
    public void checkTrouble(TroubleRequestDto troubleRequestDto) {
        if (troubleRequestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목입력은 필수값입니다.");
        } else if (troubleRequestDto.getContents().isEmpty()) {
            throw new IllegalArgumentException("내용입력은 필수값입니다.");
        } else if (troubleRequestDto.getQuestion().isEmpty()) {
            throw new IllegalArgumentException("질문자세대 입력은 필수값입니다.");
        } else if (troubleRequestDto.getAnswer().isEmpty()) {
            throw new IllegalArgumentException("답변자세대 입력은 필수값입니다.");
        }
    }

}
