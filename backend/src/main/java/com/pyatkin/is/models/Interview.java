package com.pyatkin.is.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Interview")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewId;

    @Column(name = "InterviewEvaluation", nullable = true)
    private Integer interviewEvaluation;

    @Column(name = "ConversationEvaluation", nullable = true)
    private Integer conversationEvaluation;

    @Column(name = "RecommendationEvaluation", nullable = true)
    private Integer recommendationEvaluation;

    @Column(name = "Comment", length = 1000, nullable = true) // Добавляем поле комментария
    private String comment; // Поле комментария

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidateId", referencedColumnName = "candidateId", nullable = false)
    @JsonBackReference
    //@JsonIgnoreProperties("interviews")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancyId", referencedColumnName = "vacancyId", nullable = false)
    //@JsonIgnoreProperties("interviews")
    @JsonBackReference
    private Vacancy vacancy;

    // Геттеры и сеттеры
}
