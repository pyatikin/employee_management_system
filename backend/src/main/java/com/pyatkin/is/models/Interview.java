package com.pyatkin.is.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.View;

@Entity
@Table(name = "Interview")
@Data
@AllArgsConstructor
@NoArgsConstructor
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "interviewId")*/
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewId;

    @Column(name = "InterviewEvaluation", nullable = true)
    private Double interviewEvaluation;

    @Column(name = "ConversationEvaluation", nullable = true)
    private Double conversationEvaluation;

    @Column(name = "RecommendationEvaluation", nullable = true)
    private Double recommendationEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidateId", referencedColumnName = "candidateId")
    //@JsonManagedReference("candidates")
    @JsonIgnoreProperties("interviews")
    //@JsonBackReference("candidates")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancyId", referencedColumnName = "vacancyId")
    //@JsonManagedReference("vacancies")
    @JsonIgnoreProperties("interviews")
    //@JsonBackReference("vacancies")
    private Vacancy vacancy;

    // Геттеры и сеттеры
}
