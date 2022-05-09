package com.example.seesaw.repository;

import com.example.seesaw.model.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    Mbti findByMbtiName(String mbtiName);
}
