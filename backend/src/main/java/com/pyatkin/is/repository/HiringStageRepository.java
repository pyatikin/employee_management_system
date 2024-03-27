package com.pyatkin.is.repository;

import com.pyatkin.is.models.HiringStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiringStageRepository extends JpaRepository<HiringStage, Long> {
    HiringStage findByName(String name);
}
