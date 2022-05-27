package com.example.seesaw.dictionary.service;

import com.example.seesaw.dictionary.dto.PostListResponseDto;
import com.example.seesaw.dictionary.model.Post;
import com.example.seesaw.dictionary.model.PostImage;
import com.example.seesaw.dictionary.model.PostScrap;
import com.example.seesaw.user.model.User;
import com.example.seesaw.dictionary.repository.PostImageRepository;
import com.example.seesaw.dictionary.repository.PostRepository;
import com.example.seesaw.dictionary.repository.PostScrapRepository;
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
