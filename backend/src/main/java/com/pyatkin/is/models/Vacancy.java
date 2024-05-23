package com.pyatkin.is.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private List<Interview> interviews = new ArrayList<>();

    @Transient
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DepartmentId", referencedColumnName = "departmentId", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stageId", referencedColumnName = "stageId", nullable = false)
    private HiringStage stageId;

    @Column(name = "StartDate", nullable = true) // Добавляем поле с датой начала поиска
    private LocalDate startDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidateId", referencedColumnName = "candidateId")
    @JsonBackReference
    private Candidate candidate;
}
