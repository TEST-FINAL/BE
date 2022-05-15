package com.example.seesaw.repository;

import com.example.seesaw.model.TroubleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TroubleTagRepository extends JpaRepository<TroubleTag, Long> {

    List<TroubleTag> findAllByTroubleId(Long troubleId);

    @Transactional
    void deleteAllByTroubleId(Long troubleId);

}
