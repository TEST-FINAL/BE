package com.example.seesaw.user.repository;

import com.example.seesaw.user.model.UserProfileNum;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserProfileNumRepository extends JpaRepository<UserProfileNum, Long> {

    List<UserProfileNum> findAllByUserId(Long id);

    @Transactional
    void deleteAllByUserId(Long id);

}
