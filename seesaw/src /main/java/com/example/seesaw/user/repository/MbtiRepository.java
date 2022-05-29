package com.example.seesaw.user.repository;

import com.example.seesaw.user.model.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {

    Mbti findByMbtiName(String mbtiName);

}
