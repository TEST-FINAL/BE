package com.example.seesaw.trouble.service;

import com.example.seesaw.trouble.dto.TroubleCommentRequestDto;
import com.example.seesaw.trouble.model.Trouble;
import com.example.seesaw.trouble.model.TroubleComment;
import com.example.seesaw.trouble.repository.TroubleCommentRepository;
import com.example.seesaw.trouble.repository.TroubleRepository;
import com.example.seesaw.user.repository.UserRepository;
import com.example.seesaw.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TroubleCommentService {

    private final TroubleRepository troubleRepository;
    private final TroubleCommentRepository troublecommentRepository;
    private final UserRepository userRepository;
    private final TroubleService troubleService;

    // 댓글 등록하기
    public TroubleCommentRequestDto registerComment(Long troubleId, TroubleCommentRequestDto troubleCommentRequestDto, User user) {
        User commentUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalStateException("해당하는 USER 가 없습니다.")
        );
        troubleCommentRequestDto.setNickname(commentUser.getNickname());
        Trouble savedTrouble = troubleRepository.findById(troubleId).orElseThrow(
                () -> new IllegalStateException("해당 게시글이 없습니다."));
        troubleCommentRequestDto.setCommentLikeCount(0L); // 0으로 초기화
        TroubleComment troubleComment = new TroubleComment(savedTrouble, troubleCommentRequestDto);
        troublecommentRepository.save(troubleComment);


        // 댓글 등록할 시 TroubleCommentDto 내용을 response 해준다.
        return troubleService.getTroubleCommentDto(user,troubleComment);
    }

    // 댓글 수정하기
    public TroubleCommentRequestDto updateComment(Long commentId, TroubleCommentRequestDto troubleCommentRequestDto, User user) {
        TroubleComment troubleComment = checkCommentUser(commentId, user);
        troubleComment.setNickname(user.getNickname());
        troubleComment.setComment(troubleCommentRequestDto.getComment());
        troublecommentRepository.save(troubleComment);
        // 댓글 등록할 시 TroubleCommentDto 내용을 response 해준다.
        return troubleService.getTroubleCommentDto(user,troubleComment);
    }

    // 댓글 삭제하기
    public TroubleCommentRequestDto deleteComment(Long commentId, User user) {
        TroubleComment troubleComment = checkCommentUser(commentId, user);
        List<TroubleComment> troubleCommentList = troublecommentRepository.findAllByTroubleIdOrderByCreatedAtDesc(troubleComment.getTrouble().getId());
        int index = troubleCommentList.indexOf(troubleComment); // index 0,1,2,3/4,5,6,7/8,9,10,11/12,13,14,15
        troublecommentRepository.deleteById(commentId);
        int a = index / 4 +1;                             // 삭제한 댓글의 쪽을 구한다.
        if(a == (troubleCommentList.size()-1)/ 4 +1){                   // 댓글이 4개 이하일 경우는 댓글 개수만 보낸다.
            return null;
        }
        return troubleService.getTroubleCommentDto(user, troubleCommentList.get(a*4));    // 쪽에 4를 곱해서 다음 페이지의 맨 처음 댓글을 가져온다.
    }

    // 댓글 유저 확인하기
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
