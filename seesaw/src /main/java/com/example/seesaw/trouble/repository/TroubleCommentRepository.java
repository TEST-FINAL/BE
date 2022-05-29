package com.example.seesaw.trouble.repository;

import com.example.seesaw.trouble.model.TroubleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TroubleCommentRepository extends JpaRepository<TroubleComment, Long> {

    List<TroubleComment> findAllByNickname(String nickname);

    Page<TroubleComment> findAllByTroubleIdOrderByCreatedAtDesc(Long troubleId, Pageable pageable);

    List<TroubleComment> findAllByTroubleId(Long id);

    long countByTroubleId(Long id);

    // 고민몬든걸 최신순으로 가져오기
    List<TroubleComment> findAllByTroubleIdOrderByCreatedAtDesc(Long troubleId);
}
