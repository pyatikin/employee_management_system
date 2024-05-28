package com.pyatkin.is.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyRequest {
    private String name;
    private String description;
    private Double salary;
    private LocalDate hiringDeadline;
    private String experience;
    private Long departmentId;
}