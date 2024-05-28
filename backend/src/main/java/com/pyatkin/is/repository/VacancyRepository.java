package com.pyatkin.is.repository;


import com.pyatkin.is.models.HiringStage;
import com.pyatkin.is.models.Vacancy;
import com.pyatkin.is.models.VacancyStatusReportItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findAllByStageId(HiringStage stage);
    Long countVacanciesByStageId(HiringStage stage);
    Long countByStartDateBetween(LocalDate start, LocalDate end);
    List<Vacancy> findAllByStartDateBetween(LocalDate start, LocalDate end);
}
