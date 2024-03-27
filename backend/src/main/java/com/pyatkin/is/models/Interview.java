package com.pyatkin.is.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Interview")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewId;

    @Column(name = "InterviewEvaluation", nullable = false)
    private Double interviewEvaluation;

    @Column(name = "ConversationEvaluation", nullable = false)
    private Double conversationEvaluation;

    @Column(name = "RecommendationEvaluation", nullable = false)
    private Double recommendationEvaluation;

    // Геттеры и сеттеры
}
