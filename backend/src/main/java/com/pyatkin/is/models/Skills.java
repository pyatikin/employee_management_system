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
@Table(name = "Skills")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillsId;

    @Column(name = "Name", unique = true, nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    // Геттеры и сеттеры

    // Поле для связи многие ко многим с кандидатами
    @ManyToMany(mappedBy = "skills")
    private Set<Candidate> candidates = new HashSet<>();
}
