package com.example.seesaw.repository;

import com.example.seesaw.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByCharId(Long charId);
}
