package com.pyatkin.is.repository;

import com.pyatkin.is.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findAllBySkillsIn(List<Long> skills);
}
