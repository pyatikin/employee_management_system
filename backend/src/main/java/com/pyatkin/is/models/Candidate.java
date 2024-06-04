package com.pyatkin.is.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(property = "jsonCandidateId", generator = ObjectIdGenerators.IntSequenceGenerator.class)
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

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Nationality")
    private String nationality;

    @Column(name = "Education")
    private String education;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "SearchStatus")
    private String searchStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Candidate_Resume",
            joinColumns = @JoinColumn(name = "candidateId"),
            inverseJoinColumns = @JoinColumn(name = "resumeId"))
    private Set<Resume> resumes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Candidate_Skills",
            joinColumns = @JoinColumn(name = "candidateId"),
            inverseJoinColumns = @JoinColumn(name = "skillsId"))
    private Set<Skills> skills = new HashSet<>();

}
