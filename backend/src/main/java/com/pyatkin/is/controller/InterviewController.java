package com.pyatkin.is.controllers;


import com.pyatkin.is.models.Candidate;
import com.pyatkin.is.models.Interview;
import com.pyatkin.is.models.InterviewData;
import com.pyatkin.is.models.Vacancy;
import com.pyatkin.is.repository.CandidateRepository;
import com.pyatkin.is.repository.InterviewRepository;
import com.pyatkin.is.repository.VacancyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/interviews")
public class InterviewController {


    private final InterviewRepository interviewRepository;
    private final VacancyRepository vacancyRepository;
    private final CandidateRepository candidateRepository;

    public InterviewController(InterviewRepository interviewRepository, VacancyRepository vacancyRepository, CandidateRepository candidateRepository) {
        this.interviewRepository = interviewRepository;
        this.vacancyRepository = vacancyRepository;
        this.candidateRepository = candidateRepository;
    }

    @PostMapping("/{vacancyId}/add-candidate")
    public ResponseEntity<String> addCandidateToInterview(@PathVariable Long vacancyId, @RequestBody Map<String, String> body) {
        Long candidateId = Long.valueOf(body.get("candidateId"));
        try {
            Vacancy vacancy = vacancyRepository.findById(vacancyId)
                    .orElseThrow(() -> new EntityNotFoundException("Vacancy not found with id: " + vacancyId));

            Candidate candidate = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + candidateId));

            Interview interview = new Interview();
            interview.setVacancy(vacancy);
            interview.setCandidate(candidate);

            interviewRepository.save(interview);

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add candidate to interview: " + e.getMessage());
        }
    }

    @GetMapping("/{vacancyId}/candidates")
    public ResponseEntity<List<Candidate>> getCandidatesForVacancy(@PathVariable Long vacancyId) {
        try {
            Vacancy vacancies = vacancyRepository.findById(vacancyId).orElseThrow();
            List<Interview> interviews = interviewRepository.findAllByVacancy(vacancies);// Получаем список кандидатов на собеседование для указанной вакансии

            List<Candidate> candidates = interviews.stream().map(Interview::getCandidate).toList();
            return ResponseEntity.ok(candidates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{vacancyId}/candidates/{candidateId}")
    public ResponseEntity<Interview> getInterviewForCandidateAndVacancy(@PathVariable Long candidateId, @PathVariable Long vacancyId) {
        try {
            Candidate candidate = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + candidateId));

            Vacancy vacancy = vacancyRepository.findById(vacancyId)
                    .orElseThrow(() -> new EntityNotFoundException("Vacancy not found with id: " + vacancyId));

            Interview interview = interviewRepository.findByCandidateAndVacancy(candidate, vacancy);

            return ResponseEntity.ok(interview);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{interviewId}")
    public ResponseEntity<Interview> updateInterview(@PathVariable Long interviewId, @RequestBody InterviewData updatedInterview) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found with id " + interviewId));

        interview.setInterviewEvaluation(updatedInterview.getInterviewEvaluation());
        interview.setConversationEvaluation(updatedInterview.getConversationEvaluation());
        interview.setRecommendationEvaluation(updatedInterview.getRecommendationEvaluation());
        interview.setComment(updatedInterview.getComment());

        final Interview updatedInterviewEntity = interviewRepository.save(interview);
        return ResponseEntity.ok(updatedInterviewEntity);
    }





    // Другие методы для работы с таблицей Interview (создание, получение, обновление и т.д.)
}
