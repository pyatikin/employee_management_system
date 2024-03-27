package com.pyatkin.is.repository;

import com.pyatkin.is.models.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
