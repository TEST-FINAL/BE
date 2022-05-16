package com.example.seesaw.repository;

import com.example.seesaw.model.TroubleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TroubleImageRepository extends JpaRepository<TroubleImage, Long> {

    @Transactional
    void deleteByTroubleImage(String lastImage);

    List<TroubleImage> findAllByTroubleId(Long troubleId);

    @Transactional
    void deleteAllByTroubleId(Long troubleId);

}
