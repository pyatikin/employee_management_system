package com.pyatkin.is.controller;

import com.pyatkin.is.models.Candidate;

import com.pyatkin.is.models.CandidateRequest;
import com.pyatkin.is.models.Resume;
import com.pyatkin.is.models.Skills;
import com.pyatkin.is.repository.CandidateRepository;

import com.pyatkin.is.repository.ResumeRepository;
import com.pyatkin.is.repository.SkillsRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateRepository candidateRepository;
    private final SkillsRepository skillsRepository;
    private final ResumeRepository resumeRepository;

    @Autowired
    public CandidateController(CandidateRepository candidateRepository, SkillsRepository skillsRepository, ResumeRepository resumeRepository) {
        this.candidateRepository = candidateRepository;
        this.skillsRepository = skillsRepository;
        this.resumeRepository = resumeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> searchCandidates(
            @RequestParam(name = "skillIds", required = false) List<Long> skillIds) {
        try {
            List<Candidate> candidates;

            // Если навыки указаны, выполняем поиск кандидатов по имени и выбранным навыкам
            if (skillIds != null && !skillIds.isEmpty()) {
                candidates = candidateRepository.findAllBySkillsIn(skillIds);
                // Загружаем навыки и резюме для каждого кандидата
                candidates.forEach(candidate -> {
                    candidate.getSkills().size(); // Загружаем навыки
                    candidate.getResumes().size(); // Загружаем резюме
                });
            } else {
                // В противном случае выполняем поиск
                candidates = candidateRepository.findAll();
                candidates.forEach(
                        candidate -> {
                            candidate.setSkills(new HashSet<>(skillsRepository.findAllByCandidates(candidate)));
                        }
                );
                // Загружаем навыки и резюме для каждого кандидата
                candidates.forEach(candidate -> {
                    candidate.getSkills().size(); // Загружаем навыки
                    candidate.getResumes().size(); // Загружаем резюме
                });
            }

            return new ResponseEntity<>(candidates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{candidateId}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long candidateId) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        return candidateOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(
            @RequestBody CandidateRequest candidateRequest) {

        try {
            Candidate candidate = new Candidate();
            candidate.setFirstName(candidateRequest.getFirstName());
            candidate.setLastName(candidateRequest.getLastName());
            candidate.setEmail(candidateRequest.getEmail());
            candidate.setGender(candidateRequest.getGender());
            candidate.setNationality(candidateRequest.getNationality());
            candidate.setEducation(candidateRequest.getEducation());
            candidate.setPhone(candidateRequest.getPhone());
            candidate.setSearchStatus(candidateRequest.getSearchStatus());

            Resume candidateResume = new Resume();
            candidateResume.setPosition(candidateRequest.getResume().getPosition());
            candidateResume.setContent(candidateRequest.getResume().getContent());
            resumeRepository.save(candidateResume);

            for (Long skillId: candidateRequest.getSkills()) {
                candidate.getSkills().add(skillsRepository.findById(skillId).get());
            }

            candidate.getResumes().add(candidateResume);
            Candidate savedCandidate = candidateRepository.save(candidate);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedCandidate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{candidateId}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long candidateId, @RequestBody Candidate updatedCandidate) {
        Optional<Candidate> existingCandidateOptional = candidateRepository.findById(candidateId);
        if (existingCandidateOptional.isPresent()) {
            Candidate existingCandidate = existingCandidateOptional.get();
            existingCandidate.setFirstName(updatedCandidate.getFirstName());
            existingCandidate.setLastName(updatedCandidate.getLastName());
            existingCandidate.setEmail(updatedCandidate.getEmail());
            // Update other fields as needed
            Candidate savedCandidate = candidateRepository.save(existingCandidate);
            return ResponseEntity.ok(savedCandidate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long candidateId) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        if (candidateOptional.isPresent()) {
            candidateRepository.deleteById(candidateId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{candidateId}/skills")
    public ResponseEntity<List<Skills>> getCandidateSkills(@PathVariable Long candidateId) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        if (candidateOptional.isPresent()) {
            Candidate candidate = candidateOptional.get();
            List<Skills> skills = skillsRepository.findAllByCandidates(candidate);
            /*List<CandidateSkills> skillsId = candidateSkillsRepository.findAllByCandidate(candidate);
            List<Skills> skills = skillsId.stream().map(CandidateSkills::getSkills).toList();*/
            //Set<Skills> candidateSkills = skillsRepository.findAllBySkillsIdIn(skillsId);
            return new ResponseEntity<>(skills, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{candidateId}/resumes")
    public ResponseEntity<List<Resume>> getCandidateResumes(@PathVariable Long candidateId) {
        try {
            Optional<Candidate> candidateData = candidateRepository.findById(candidateId);

            if (candidateData.isPresent()) {
                Candidate candidate =  candidateData.get();
                List<Resume>resumes = resumeRepository.findAllByCandidates(candidate);
                return new ResponseEntity<>(resumes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
