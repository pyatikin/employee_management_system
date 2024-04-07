package com.pyatkin.is.controller;

import com.pyatkin.is.models.Department;
import com.pyatkin.is.models.Vacancy;
import com.pyatkin.is.repository.DepartmentRepository;
import com.pyatkin.is.repository.HiringStageRepository;
import com.pyatkin.is.repository.VacancyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyRepository vacancyRepository;
    private final DepartmentController departmentController;
    private final HiringStageRepository hiringStageRepository;

    public VacancyController(VacancyRepository vacancyRepository, DepartmentRepository departmentRepository, DepartmentController departmentController, HiringStageRepository hiringStageRepository) {
        this.vacancyRepository = vacancyRepository;
        this.departmentController = departmentController;
        this.hiringStageRepository = hiringStageRepository;
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
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Department department = departmentController.findById(vacancy.getDepartmentId());
        vacancy.setDepartment(department);
        vacancy.setStageId(hiringStageRepository.findByName("открыта"));
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


}
