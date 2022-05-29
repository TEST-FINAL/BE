package com.example.seesaw.trouble.repository;


import com.example.seesaw.trouble.model.TroubleComment;
import com.example.seesaw.trouble.model.TroubleCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface TroubleCommentLikeRepository extends JpaRepository<TroubleCommentLike, Long> {

    TroubleCommentLike findByTroubleCommentAndUserId(TroubleComment troubleComment, Long userId);

    @Transactional
    void deleteById(Long id);

}
