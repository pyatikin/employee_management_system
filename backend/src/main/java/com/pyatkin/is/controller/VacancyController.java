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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyRepository vacancyService;
    private final DepartmentController departmentController;
    private final HiringStageRepository hiringStageRepository;

    public VacancyController(VacancyRepository vacancyService, DepartmentRepository departmentRepository, DepartmentController departmentController, HiringStageRepository hiringStageRepository) {
        this.vacancyService = vacancyService;
        this.departmentController = departmentController;
        this.hiringStageRepository = hiringStageRepository;
    }

    // Получение всех вакансий
    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyService.findAll();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    // Получение вакансии по ID
    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable("id") Long id) {
        Vacancy vacancy = vacancyService.findById(id)
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
        Vacancy createdVacancy = vacancyService.save(vacancy);
        return new ResponseEntity<>(createdVacancy, HttpStatus.CREATED);
    }

    // Обновление существующей вакансии
    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable("id") Long id, @RequestBody Vacancy vacancy) {
        Vacancy updatedVacancy = vacancyService.save(vacancy);
        if (updatedVacancy != null) {
            return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Удаление вакансии по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
