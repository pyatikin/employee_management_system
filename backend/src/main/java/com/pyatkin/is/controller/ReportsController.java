package com.pyatkin.is.controller;

import com.pyatkin.is.models.HiringStage;
import com.pyatkin.is.models.Interview;
import com.pyatkin.is.models.Vacancy;
import com.pyatkin.is.models.VacancyStatusReportItem;
import com.pyatkin.is.repository.HiringStageRepository;
import com.pyatkin.is.repository.VacancyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/reports")
public class ReportsController {

    private final VacancyRepository vacancyRepository;
    private final HiringStageRepository hiringStageRepository;

    @Autowired
    public ReportsController(VacancyRepository vacancyRepository, HiringStageRepository hiringStageRepository) {
        this.vacancyRepository = vacancyRepository;
        this.hiringStageRepository = hiringStageRepository;
    }

    @GetMapping("/vacancy-status")
    public ResponseEntity<List<VacancyStatusReportItem>> getVacancyStatusReport() {
        List<HiringStage> stages = hiringStageRepository.findAll();
        List<VacancyStatusReportItem> reportItems = new ArrayList<>();
        Long all = 0L;
        for (HiringStage stage: stages) {
            Long count = vacancyRepository.countVacanciesByStageId(stage);
            all += count;
            reportItems.add(new VacancyStatusReportItem(stage.getName(), count, 0));
        }
        for (VacancyStatusReportItem item: reportItems) {
            item.setRelativeCount(1.0*item.getCount()/all);
        }

        return new ResponseEntity<>(reportItems, HttpStatus.OK);
    }


    @GetMapping("/recruitment/{vacancyId}")
    public ResponseEntity<RecruitmentSummaryReport> getRecruitmentSummaryReport(@PathVariable Long vacancyId) {
        try {
            Vacancy vacancy = vacancyRepository.findById(vacancyId)
                    .orElseThrow(() -> new EntityNotFoundException("Vacancy not found with id: " + vacancyId));

            // Вычисляем время потраченное на поиск в днях
            LocalDate startDate = vacancy.getStartDate();
            LocalDate endDate = LocalDate.now();
            long daysSpent = Period.between(startDate, endDate).getDays();

            // Получаем количество кандидатов для данной вакансии
            List<Interview> interviews = vacancy.getInterviews();
            long candidateCount = interviews.size();

            // Вычисляем количество кандидатов на рассмотрении, прошедших телефонное интервью и собеседование
            long candidatesInReview = interviews.stream().filter(interview -> interview.getInterviewEvaluation() != null).count();
            long candidatesForPhoneInterview = interviews.stream().filter(interview -> interview.getInterviewEvaluation() != null && interview.getConversationEvaluation() != null).count();
            long candidatesForInterview = interviews.stream().filter(interview -> interview.getConversationEvaluation() != null && interview.getRecommendationEvaluation() != null).count();

            // Получаем результат работы
            String recruitmentResult = "In Progress"; // Пока в процессе

            // Создаем объект отчета и устанавливаем значения
            RecruitmentSummaryReport report = new RecruitmentSummaryReport();
            report.setDaysSpent(daysSpent);
            report.setCandidateCount(candidateCount);
            report.setRecruitmentResult(recruitmentResult);
            report.setCandidatesInReview(candidatesInReview);
            report.setCandidatesForPhoneInterview(candidatesForPhoneInterview);
            report.setCandidatesForInterview(candidatesForInterview);

            return ResponseEntity.ok(report);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Data
    public class RecruitmentSummaryReport {
        private Long daysSpent; // Время потраченное на поиск в днях
        private Long candidateCount; // Количество кандидатов
        private String recruitmentResult; // Результат работы
        private Long candidatesInReview; // Количество кандидатов на рассмотрении
        private Long candidatesForPhoneInterview; // Количество кандидатов для телефонного интервью
        private Long candidatesForInterview; // Количество кандидатов на собеседовании

        // Геттеры и сеттеры
    }

}
