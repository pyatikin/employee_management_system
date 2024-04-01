package com.pyatkin.is.repository;

import com.pyatkin.is.models.Candidate;
import com.pyatkin.is.models.Interview;
import com.pyatkin.is.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findAllByVacancy(Vacancy vacancies);
}
