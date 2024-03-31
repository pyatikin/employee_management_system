package com.pyatkin.is.models;

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
    private Double interviewEvaluation;

    @Column(name = "ConversationEvaluation", nullable = true)
    private Double conversationEvaluation;

    @Column(name = "RecommendationEvaluation", nullable = true)
    private Double recommendationEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidateId", referencedColumnName = "candidateId")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancyId", referencedColumnName = "vacancyId")
    private Vacancy vacancy;

    // Геттеры и сеттеры
}
