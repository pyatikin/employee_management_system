package com.pyatkin.is.controller;

import com.pyatkin.is.models.Candidate;
import com.pyatkin.is.models.CandidateRequest;
import com.pyatkin.is.models.Department;
import com.pyatkin.is.models.HiringStage;
import com.pyatkin.is.models.Interview;
import com.pyatkin.is.models.Vacancy;
import com.pyatkin.is.models.VacancyRequest;
import com.pyatkin.is.repository.CandidateRepository;
import com.pyatkin.is.repository.DepartmentRepository;
import com.pyatkin.is.repository.HiringStageRepository;
import com.pyatkin.is.repository.InterviewRepository;
import com.pyatkin.is.repository.VacancyRepository;
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
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyRepository vacancyRepository;
    private final DepartmentController departmentController;
    private final HiringStageRepository hiringStageRepository;
    private final CandidateRepository candidateRepository;
    private final InterviewRepository interviewRepository;

    public VacancyController(VacancyRepository vacancyRepository, DepartmentRepository departmentRepository, DepartmentController departmentController, HiringStageRepository hiringStageRepository, CandidateRepository candidateRepository, InterviewRepository interviewRepository) {
        this.vacancyRepository = vacancyRepository;
        this.departmentController = departmentController;
        this.hiringStageRepository = hiringStageRepository;
        this.candidateRepository = candidateRepository;
        this.interviewRepository = interviewRepository;
    }

    // Получение всех вакансий
    @GetMapping
    public ResponseEntity<List<Vacancy>> getOpenVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAllByStageId(hiringStageRepository.findByName("открыта"));
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }
    @GetMapping({"/working-vacancies"})
    public ResponseEntity<List<Vacancy>> getInWork() {
        List<Vacancy> vacancies = vacancyRepository.findAllByStageId(hiringStageRepository.findByName("в работе"));
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    // Получение вакансии по ID
    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable("id") Long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElse(null);
        if (vacancy != null) {
            return new ResponseEntity<>(vacancy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Создание новой вакансии
    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody VacancyRequest vacancyRequest) {
        Vacancy vacancy = new Vacancy();
        Department department = departmentController.findById(vacancyRequest.getDepartmentId());
        vacancy.setDepartment(department);
        vacancy.setStageId(hiringStageRepository.findByName("открыта"));
        vacancy.setName(vacancyRequest.getName());
        vacancy.setDescription(vacancyRequest.getDescription());
        vacancy.setExperience(vacancyRequest.getExperience());
        vacancy.setSalary(vacancyRequest.getSalary());
        vacancy.setHiringDeadline(vacancyRequest.getHiringDeadline());
       //vacancy.setInterviews(null);
        vacancy.setCandidate(null);
        Vacancy createdVacancy = vacancyRepository.save(vacancy);
        return new ResponseEntity<>(createdVacancy, HttpStatus.CREATED);
    }

    // Обновление существующей вакансии
    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable("id") Long id, @RequestBody Vacancy vacancy) {
        Vacancy updatedVacancy = vacancyRepository.save(vacancy);
        if (updatedVacancy != null) {
            return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Удаление вакансии по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable("id") Long id) {
        vacancyRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{vacancyId}/takeInWork")
    public ResponseEntity<Vacancy> updateInWork(@PathVariable("vacancyId") Long vacancyId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        vacancy.setStageId(hiringStageRepository.findByName("в работе"));
        Vacancy updatedVacancy = vacancyRepository.save(vacancy);
        if (updatedVacancy != null) {
            return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{vacancyId}/status")
    public ResponseEntity<Vacancy> updateStatus(@PathVariable("vacancyId") Long vacancyId, @RequestBody Map<String, String> requestBody) {
        String newStatus = requestBody.get("newStatus");
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        vacancy.setStageId(hiringStageRepository.findByName(newStatus));
        Vacancy updatedVacancy = vacancyRepository.save(vacancy);
        if (updatedVacancy != null) {
            return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/closed")
    public List<Vacancy> getClosedVacancies() {
        HiringStage stage = hiringStageRepository.findByName("закрыта");
        return vacancyRepository.findAllByStageId(stage);
    }

    @PostMapping("/{vacancyId}/approve")
    public void approveCandidate(@PathVariable Long vacancyId, @RequestBody Long candidateId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        HiringStage closedStage = hiringStageRepository.findByName("закрыта");

        var candidate = candidateRepository.findById(candidateId);
        // Изменяем статус вакансии на "закрыта" и сохраняем кандидата в вакансии
        vacancy.setStageId(closedStage);
        vacancy.setCandidate(candidate.get());
        vacancyRepository.save(vacancy);
    }

    @GetMapping("/{vacancyId}/selected-candidate")
    public Candidate getCandidate(@PathVariable Long vacancyId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        return vacancy.getCandidate();
    }

}
