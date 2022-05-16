package com.example.seesaw.service;

import com.example.seesaw.dto.TroubleCommentRequestDto;
import com.example.seesaw.model.Trouble;
import com.example.seesaw.model.TroubleComment;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.TroubleCommentRepository;
import com.example.seesaw.repository.TroubleRepository;
import com.example.seesaw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TroubleCommentService {

    private final TroubleRepository troubleRepository;
    private final TroubleCommentRepository troublecommentRepository;
    private final UserRepository userRepository;

    public void registerComment(Long troubleId, TroubleCommentRequestDto troubleCommentRequestDto, User user) {
        User commentUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalStateException("해당하는 USER 가 없습니다.")
        );
        troubleCommentRequestDto.setNickname(commentUser.getNickname());
        Trouble savedTrouble = troubleRepository.findById(troubleId).orElseThrow(
                () -> new IllegalStateException("해당 게시글이 없습니다."));
        troubleCommentRequestDto.setLikeCount(0L);
        TroubleComment troubleComment = new TroubleComment(savedTrouble, troubleCommentRequestDto);
        troublecommentRepository.save(troubleComment);
    }

    public void updateComment(Long commentId, TroubleCommentRequestDto troubleCommentRequestDto, User user) {
        TroubleComment troubleComment = checkCommentUser(commentId, user);
        troubleComment.setNickname(user.getNickname());
        troubleComment.setComment(troubleCommentRequestDto.getComment());
        troublecommentRepository.save(troubleComment);
    }

    public void deleteComment(Long commentId, User user) {
        checkCommentUser(commentId, user);
        troublecommentRepository.deleteById(commentId);
    }

    public TroubleComment checkCommentUser(Long commentId, User user){
        User commentUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalStateException("해당하는 USER 가 없습니다.")
        );
        TroubleComment troubleComment = troublecommentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("해당 댓글이 없습니다."));
        if(!commentUser.getNickname().equals(troubleComment.getNickname())){
            throw new IllegalArgumentException("댓글 작성자가 아니므로 댓글 수정, 삭제가 불가합니다.");
        }
        return troubleComment;
    }
}
