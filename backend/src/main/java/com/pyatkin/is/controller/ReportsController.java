package com.pyatkin.is.controller;

import com.pyatkin.is.models.Candidate;
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
import org.springframework.web.bind.annotation.RequestParam;
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
            LocalDate endDate = vacancy.getEndDate();
            long daysSpent = (startDate!=null&&endDate!=null)?Period.between(startDate, endDate).getDays():0;

            // Получаем количество кандидатов для данной вакансии
            List<Interview> interviews = vacancy.getInterviews();
            long candidateCount = interviews.size();

            // Вычисляем количество кандидатов на рассмотрении, прошедших телефонное интервью и собеседование
            long candidatesWithInterview = interviews.stream().filter(interview -> interview.getInterviewEvaluation() != null).count();
            long candidatesWithConversation = interviews.stream().filter(interview -> interview.getConversationEvaluation() != null).count();

            // Получаем результат работы
            String recruitmentResult = vacancy.getStageId().getName(); // Пока в процессе

            // Создаем объект отчета и устанавливаем значения
            RecruitmentSummaryReport report = new RecruitmentSummaryReport();
            report.setName(vacancy.getName());
            report.setDaysSpent(daysSpent);
            report.setCandidateCount(candidateCount);
            report.setRecruitmentResult(recruitmentResult);
            report.setCandidatesWithInterview(candidatesWithInterview!=0?(double)candidatesWithInterview/candidateCount*100:0);
            report.setCandidatesWithConversation(candidatesWithConversation!=0?(double)candidatesWithConversation/candidateCount*100:0);
            report.setCandidate(vacancy.getCandidate());

            return ResponseEntity.ok(report);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Data
    public class RecruitmentSummaryReport {
        private String name;
        private Long daysSpent; // Время потраченное на поиск в днях
        private Long candidateCount; // Количество кандидатов
        private String recruitmentResult; // Результат работы
        private Double candidatesWithConversation; // Количество кандидатов для телефонного интервью
        private Double candidatesWithInterview; // Количество кандидатов на собеседовании
        private Candidate candidate;

        // Геттеры и сеттеры
    }

   /* @GetMapping("/summary")
    public ResponseEntity<SummaryReport> getSummaryReport(
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate
    ) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            long totalVacancies = vacancyRepository.countByStartDateBetween(start, end);
            List<Vacancy> vacancyList = vacancyRepository.findAllByStartDateBetween(start, end);
            long totalCandidates = vacancyRepository.countTotalCandidatesByStartDateBetween(start, end);
            List<HiringStage> hiringStages = hiringStageRepository.findAll();

            SummaryReport report = new SummaryReport();
            report.setTotalVacancies(totalVacancies);
            report.setTotalCandidates(totalCandidates);
            report.setHiringStages(hiringStages);

            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public static class SummaryReport {
        private long totalVacancies;
        private long totalCandidates;
        private List<HiringStage> hiringStages;

        // Getters and setters

        public long getTotalVacancies() {
            return totalVacancies;
        }

        public void setTotalVacancies(long totalVacancies) {
            this.totalVacancies = totalVacancies;
        }

        public long getTotalCandidates() {
            return totalCandidates;
        }

        public void setTotalCandidates(long totalCandidates) {
            this.totalCandidates = totalCandidates;
        }

        public List<HiringStage> getHiringStages() {
            return hiringStages;
        }

        public void setHiringStages(List<HiringStage> hiringStages) {
            this.hiringStages = hiringStages;
        }
    }*/

}
