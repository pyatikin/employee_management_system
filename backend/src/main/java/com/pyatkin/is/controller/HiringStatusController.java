package com.pyatkin.is.controller;

import com.pyatkin.is.models.HiringStage;
import com.pyatkin.is.repository.HiringStageRepository;
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
@RequestMapping("/hiring-status")
public class HiringStatusController {
    private final HiringStageRepository hiringStageRepository;

    public HiringStatusController(HiringStageRepository hiringStageRepository) {
        this.hiringStageRepository = hiringStageRepository;
    }

    @GetMapping
    public List<HiringStage> getAllHiringStages() {
        return hiringStageRepository.findAll();
    }

    @GetMapping("/{stageId}")
    public HiringStage getHiringStageById(@PathVariable Long stageId) {
        Optional<HiringStage> stage = hiringStageRepository.findById(stageId);
        return stage.orElse(null); // Вернуть найденный этап или null, если не найден
    }

    @PostMapping
    public HiringStage createHiringStage(@RequestBody HiringStage hiringStage) {
        return hiringStageRepository.save(hiringStage);
    }

    @PutMapping("/{stageId}")
    public HiringStage updateHiringStage(@PathVariable Long stageId, @RequestBody HiringStage hiringStage) {
        return hiringStageRepository.save(hiringStage);
    }

    @DeleteMapping("/{stageId}")
    public void deleteHiringStage(@PathVariable Long stageId) {
        hiringStageRepository.deleteById(stageId);
    }
}
