package com.pyatkin.is.models;

import com.pyatkin.is.controller.CandidateController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * todo Document type CandidateRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequest {
    private String email;
    private String firstName;
    private String lastName;
    private ResumeData resume;
    private List<Long> skills;

    // Геттеры и сеттеры
}