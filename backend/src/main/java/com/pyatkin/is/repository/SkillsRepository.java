package com.pyatkin.is.repository;

import com.pyatkin.is.models.Candidate;
import com.pyatkin.is.models.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
    List<Skills> findAllByCandidates(Candidate candidate);
    List<Skills> findAllBySkillsIdIn(List<Long> id);
}
