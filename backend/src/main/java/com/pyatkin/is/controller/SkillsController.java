package com.pyatkin.is.controller;


import com.pyatkin.is.models.Skills;
import com.pyatkin.is.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
