package com.example.seesaw.service;

import com.example.seesaw.dto.PostListResponseDto;
import com.example.seesaw.model.Post;
import com.example.seesaw.model.PostImage;
import com.example.seesaw.model.PostScrap;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.PostImageRepository;
import com.example.seesaw.repository.PostRepository;
import com.example.seesaw.repository.PostScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostScrapService {

    private final PostRepository postRepository;
    private final PostScrapRepository postScrapRepository;
    private final PostImageRepository postImageRepository;

    @Transactional
    public PostListResponseDto scrapPost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Error : post is not found"));

        PostScrap postScrap = postScrapRepository.findByUserAndPost(user, post);
        List<PostImage> postImages = postImageRepository.findAllByPostId(post.getId());
        boolean scrapStatus;

        if (postScrap != null) {
            postScrapRepository.deleteById(postScrap.getId());
            post.setScrapCount(post.getScrapCount() - 1);
            postRepository.save(post);
            scrapStatus = false;
        } else {
            PostScrap scrap = new PostScrap(user, post);
            postScrapRepository.save(scrap);
            post.setScrapCount(post.getScrapCount() + 1);
            postRepository.save(post);
            scrapStatus = true;
        }
        return new PostListResponseDto(post, scrapStatus, postImages.get(0).getPostImage());
    }
}
