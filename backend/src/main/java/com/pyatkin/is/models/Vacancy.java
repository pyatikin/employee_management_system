package com.pyatkin.is.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Vacancy")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(property = "jsonVacancyId", generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacancyId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "Salary", nullable = false)
    private Double salary;

    @Column(name = "HiringDeadline", nullable = false)
    private LocalDate hiringDeadline;

    @Column(name = "Experience", nullable = false)
    private String experience;

    @Transient
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DepartmentId", referencedColumnName = "departmentId", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stageId", referencedColumnName = "stageId", nullable = false)
    private HiringStage stageId;

    @Column(name = "StartDate", nullable = true)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = true)
    private LocalDate endDate;

    @OneToOne(targetEntity = Candidate.class)
    private Candidate candidate;
}
