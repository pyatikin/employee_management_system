package com.pyatkin.is.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    //@ToString.Exclude
    @JsonManagedReference
    private List<Interview> interviews = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Candidate_Resume",
            joinColumns = @JoinColumn(name = "candidateId"),
            inverseJoinColumns = @JoinColumn(name = "resumeId"))
    @JsonIgnoreProperties("candidates")
    //@ToString.Exclude
    private Set<Resume> resumes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Candidate_Skills",
            joinColumns = @JoinColumn(name = "candidateId"),
            inverseJoinColumns = @JoinColumn(name = "skillsId"))
    @JsonIgnoreProperties("candidates")
    //@ToString.Exclude
    private Set<Skills> skills = new HashSet<>();

    @OneToOne(mappedBy = "candidate", fetch = FetchType.LAZY)
    //@ToString.Exclude
    @JsonManagedReference
    private Vacancy vacancies;
}
