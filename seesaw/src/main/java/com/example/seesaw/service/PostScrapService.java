package com.example.seesaw.service;

import com.example.seesaw.model.Post;
import com.example.seesaw.model.PostScrap;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.PostRepository;
import com.example.seesaw.repository.PostScrapRepository;
import com.example.seesaw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostScrapService {

    private final PostRepository postRepository;
    private final PostScrapRepository postScrapRepository;

    @Transactional
    public boolean scrapPost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Error : post is not found"));

        PostScrap postScrap = postScrapRepository.findByUserAndPost(user, post);

        if (postScrap != null) {
            postScrapRepository.deleteById(postScrap.getId());
            post.setScrapCount(post.getScrapCount() - 1);
            postRepository.save(post);
            return false;
        } else {
            PostScrap scrap = new PostScrap(user, post);
            postScrapRepository.save(scrap);
            post.setScrapCount(post.getScrapCount() + 1);
            postRepository.save(post);
        }
        return true;
    }
}
