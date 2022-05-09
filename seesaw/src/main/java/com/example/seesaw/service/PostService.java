package com.example.seesaw.service;

import com.example.seesaw.dto.PostRequestDto;
import com.example.seesaw.dto.PostResponseDto;
import com.example.seesaw.model.Post;
import com.example.seesaw.model.PostImage;
import com.example.seesaw.model.PostTag;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.PostImageRepository;
import com.example.seesaw.repository.PostRepository;
import com.example.seesaw.repository.PostTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostTagRepository postTagRepository;
    private final PostS3Service postS3Service;


    //단어장 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, List<MultipartFile> files, User user) {

        // 단어장 작성시 이미지파일 없이 등록시 기본 이미지 파일로 올리기!
        String name = null;
        for(MultipartFile file:files){
            name = file.getOriginalFilename();
            System.out.println("file이름은~~~:" + name);
        }
        List<String> imagePaths = new ArrayList<>();
        if (name.equals("")) {
            imagePaths.add("기본이미지 AWS에 저장해서 주소넣기!");
        } else {
            imagePaths.addAll(postS3Service.upload(files));

        }
        // 단어
        String title = requestDto.getTitle();
        // 내용
        String contents = requestDto.getContents();
        // videoUrl
        String videoUrl = requestDto.getVideoUrl();
        // 태그
        List<String> tagName = requestDto.getTagNames();

        if (title == null) {
            throw new IllegalArgumentException("등록할 단어를 적어주세요.");
        } else if (contents == null) {
            throw new IllegalArgumentException("등록할 단어의 내용을 적어주세요.");
        }

        //post 저장
        Post post = new Post(title, contents, videoUrl, user);
        postRepository.save(post);

        //이미지 URL 저장하기
        List<String> images = new ArrayList<>();
        for(String imageUrl : imagePaths){
            PostImage postImage = new PostImage(imageUrl, post);
            postImageRepository.save(postImage);
            images.add(postImage.getImageUrl());
        }

        // tag 저장하기.
        List<String> tags = new ArrayList<>();
        for(String tagNames : tagName){
            PostTag postTag = new PostTag(tagNames, post);
            postTagRepository.save(postTag);
            tags.add(postTag.getTagName());
        }

        //return 값 생성
        return new PostResponseDto(post, images, tags);
    }

    // 중복체크
    public boolean wordCheck(String title) {
        return postRepository.existsByTitle(title);
    }

    // 단어장 수정
    public void updatePost(Long postId, PostRequestDto requestDto,PostResponseDto responseDto, List<MultipartFile> files, User user) {

        // 해당 단어장 체크.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 단어장이 없습니다.")
        );
        //단어 내용 수정시 null 불가.
        if (responseDto.getTitle() != requestDto.getTitle()){
            throw new IllegalArgumentException("단어는 변경할 수 없습니다.");
        }
        else if (requestDto.getContents() == null) {
            throw new IllegalArgumentException("등록할 단어의 내용을 적어주세요.");
        }

        //post update
        post.update(requestDto, user);

        // 이미지 name 확인
        String name = null;
        for(MultipartFile file:files){
            name = file.getOriginalFilename();
            System.out.println("file이름은~~~:" + name);
        }
        // 이미지 수정작업
        List<String> imagePaths = new ArrayList<>();
        if (name.equals("") && requestDto.getPostImages().get(0).isEmpty()) {
            imagePaths.add("기본이미지 AWS에 저장해서 주소넣기!");
            postImageRepository.deleteAllByPostId(postId);
        } else if(!name.equals("")) {
            imagePaths.addAll(postS3Service.update(postId, requestDto.getPostImages(), files));
        } else{
            imagePaths = requestDto.getPostImages();
            postImageRepository.deleteAllByPostId(postId);
        }
        //이미지 URL 저장
        for(String imageUrl : imagePaths){
            PostImage postImage = new PostImage(imageUrl, post);
            postImageRepository.save(postImage);

        }
        // tag 저장
        List<String> tagName = requestDto.getTagNames();
        postTagRepository.deleteAllByPostId(postId);
        for(String tagNames : tagName){
            PostTag postTag = new PostTag(tagNames, post);
            postTagRepository.save(postTag);
        }

    }

}
