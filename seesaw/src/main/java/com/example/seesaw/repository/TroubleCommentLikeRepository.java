package com.example.seesaw.repository;


import com.example.seesaw.model.PostCommentLike;
import com.example.seesaw.model.TroubleComment;
import com.example.seesaw.model.TroubleCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface TroubleCommentLikeRepository extends JpaRepository<TroubleCommentLike, Long> {

    TroubleCommentLike findByTroubleCommentAndUserId(TroubleComment troubleComment, Long userId);

    @Transactional
    void deleteById(Long id);

}
