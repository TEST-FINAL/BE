package com.example.seesaw.trouble.repository;

import com.example.seesaw.trouble.model.TroubleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TroubleTagRepository extends JpaRepository<TroubleTag, Long> {

    List<TroubleTag> findAllByTroubleId(Long troubleId);

    @Transactional
    void deleteAllByTroubleId(Long troubleId);

}
