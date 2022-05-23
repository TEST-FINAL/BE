package com.example.seesaw.repository;

import com.example.seesaw.model.Trouble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TroubleRepository extends JpaRepository<Trouble, Long> {

    List<Trouble> findAllByUserId(Long id);

    List<Trouble> findAllByOrderByCreatedAtDesc();

    List<Trouble> findAllByOrderByViewsDesc();

    Page<Trouble> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Trouble> findAllByAnswerOrderByViewsDesc(String generation);

}
