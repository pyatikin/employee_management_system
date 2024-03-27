package com.pyatkin.is.repository;

import com.pyatkin.is.models.CandidateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateStatusRepository extends JpaRepository<CandidateStatus, Long> {
}
