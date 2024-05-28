package com.pyatkin.is.controller;


import com.pyatkin.is.models.SkillRequest;
import com.pyatkin.is.models.Skills;
import com.pyatkin.is.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/skills")
public class SkillsController {

    private final SkillsRepository skillsRepository;

    @Autowired
    public SkillsController(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    @GetMapping
    public List<Skills> getAllSkills() {
        return skillsRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Skills> addSkill(@RequestBody Skills skill) {
        return ResponseEntity.ok(skillsRepository.save(skill));
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Long id) {
        skillsRepository.deleteById(id);
    }
}
