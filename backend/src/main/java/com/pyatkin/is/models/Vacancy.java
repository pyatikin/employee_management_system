package com.pyatkin.is.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Vacancy")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VacancyId", nullable = false)
    private Long vacancyId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "Salary", nullable = false)
    private Double salary;

    @Column(name = "HiringDeadline", nullable = false)
    private LocalDate hiringDeadline;

    @Column(name = "Candidate", unique = true)
    private String candidate;

    @ManyToOne
    @JoinColumn(name = "DepartmentId", nullable = false)
    private Department department;

    // Getters and setters
}
