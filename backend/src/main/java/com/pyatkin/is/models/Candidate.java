package com.pyatkin.is.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Candidate")
@Data
@AllArgsConstructor
@NoArgsConstructor
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "candidateId")*/
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


    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    //@JsonBackReference("candidates")
    //@JsonIgnore
    //@JsonManagedReference("candidates")
    private List<Interview> interviews = new ArrayList<>();

    // Геттеры и сеттеры

    // Поле для связи многие ко многим с резюме
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Candidate_Resume",
            joinColumns = @JoinColumn(name = "candidateId"),
            inverseJoinColumns = @JoinColumn(name = "resumeId"))
    @JsonIgnoreProperties("candidates")
    private Set<Resume> resumes = new HashSet<>();

    // Поле для связи многие ко многим со скиллами
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Candidate_Skills",
            joinColumns = @JoinColumn(name = "candidateId"),
            inverseJoinColumns = @JoinColumn(name = "skillsId"))
    @JsonIgnoreProperties("candidates")
    private Set<Skills> skills = new HashSet<>();
}
