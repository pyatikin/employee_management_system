package com.pyatkin.is.repository;

import com.pyatkin.is.models.Candidate;
import com.pyatkin.is.models.Resume;
import com.pyatkin.is.models.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findAllByCandidates(Candidate candidate);
}
