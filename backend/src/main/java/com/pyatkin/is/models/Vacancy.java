package com.pyatkin.is.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.LAZY)
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

    // Геттеры и сеттеры
}
