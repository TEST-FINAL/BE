package com.example.seesaw.repository;

import com.example.seesaw.model.TroubleComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TroubleCommentRepository extends JpaRepository<TroubleComment, Long> {

    List<TroubleComment> findAllByNickname(String nickname);

    List<TroubleComment> findAllByTroubleIdOrderByLikeCountDesc(Long troubleId);

    List<TroubleComment> findAllByTroubleId(Long id);

}
