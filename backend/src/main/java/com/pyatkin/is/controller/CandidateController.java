package com.pyatkin.is.controller;

import com.pyatkin.is.models.Candidate;
import com.pyatkin.is.repository.CandidateRepository;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateController(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long candidateId) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        return candidateOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        Candidate savedCandidate = candidateRepository.save(candidate);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCandidate);
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
}
