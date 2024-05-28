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
    private Long candidateId;
    private String firstName;
    private String lastName;
    private ResumeData resume;
    private String email;
    private String gender;
    private String nationality;
    private String education;
    private String phone;
    private String searchStatus;
    private List<Long> skills;

    // Геттеры и сеттеры
}