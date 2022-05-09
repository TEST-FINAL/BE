package com.example.seesaw.repository;

import com.example.seesaw.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Transactional
    void deleteByUserId(Long id);
}
