package com.pyatkin.is.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "vacancyId", scope=Vacancy.class)*/
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

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.LAZY)
    //@JsonBackReference("vacancies")
    @JsonIgnore
    private List<Interview> interviews = new ArrayList<>();

    @Transient
    private Long departmentId; // Временное поле для хранения идентификатора департамента

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DepartmentId", referencedColumnName = "departmentId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY) // Ленивая загрузка, чтобы избежать избыточных запросов
    @JoinColumn(name = "stageId", referencedColumnName = "stageId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private HiringStage stageId; // Связь с таблицей HiringStage по полю hiringStageId

    // Геттеры и сеттеры
}
