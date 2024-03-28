package com.pyatkin.is.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Resume")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @Column(name = "Position", nullable = false)
    private String position;

    @Column(name = "Content", nullable = false)
    private String content;

    // Геттеры и сеттеры

    // Поле для связи многие ко многим с кандидатами
    @ManyToMany(mappedBy = "resumes")
    private Set<Candidate> candidates = new HashSet<>();
}
