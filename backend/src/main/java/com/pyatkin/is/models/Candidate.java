package com.pyatkin.is.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Candidate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    // Геттеры и сеттеры

    // Поле для связи многие ко многим с резюме
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Candidate_Resume",
            joinColumns = @JoinColumn(name = "candidateId"),
            inverseJoinColumns = @JoinColumn(name = "resumeId"))
    private Set<Resume> resumes = new HashSet<>();

    // Поле для связи многие ко многим со скиллами
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Candidate_Skills",
            joinColumns = @JoinColumn(name = "candidateId"),
            inverseJoinColumns = @JoinColumn(name = "skillsId"))
    private Set<Skills> skills = new HashSet<>();
}
