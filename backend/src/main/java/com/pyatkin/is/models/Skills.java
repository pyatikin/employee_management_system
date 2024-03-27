package com.pyatkin.is.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Skills")
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillsId;

    @Column(name = "Name", unique = true, nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    // Геттеры и сеттеры
}
