package com.pyatkin.is.controller;

import com.pyatkin.is.models.HiringStage;
import com.pyatkin.is.models.VacancyStatusReportItem;
import com.pyatkin.is.repository.HiringStageRepository;
import com.pyatkin.is.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
