package com.pyatkin.is.repository;


import com.pyatkin.is.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
