package com.example.seesaw.service;

import com.example.seesaw.model.TroubleComment;
import com.example.seesaw.model.TroubleLike;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.TroubleCommentRepository;
import com.example.seesaw.repository.TroubleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class TroubleLikeService {

    private final TroubleCommentRepository troubleCommentRepository;
    private final TroubleLikeRepository troubleLikeRepository;

    @Transactional
    public boolean getLikes(Long commentId, User user) {
        Long userId= user.getId();
        TroubleComment troubleComment = troubleCommentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );

        TroubleLike savedLike = troubleLikeRepository.findByTroubleCommentAndUserId(troubleComment, userId);

        if(savedLike != null){
            troubleLikeRepository.deleteById(savedLike.getId());
            troubleComment.setLikeCount(troubleComment.getLikeCount()-1); //고민댓글 좋아요 수 -1
            troubleCommentRepository.save(troubleComment);
            return false;
        } else{
            TroubleLike troubleLike = new TroubleLike(user, troubleComment);
            troubleLikeRepository.save(troubleLike);
            troubleComment.setLikeCount(troubleComment.getLikeCount()+1); //고민댓글 좋아요 수 +1
            troubleCommentRepository.save(troubleComment);
            return true;
        }
    }

    @Transactional
    public boolean likeStatus(Long commentId, User user) {
        Long userId= user.getId();
        TroubleComment troubleComment = troubleCommentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다.")
        );

        TroubleLike savedLike = troubleLikeRepository.findByTroubleCommentAndUserId(troubleComment, userId);
        return savedLike != null;
    }
}
