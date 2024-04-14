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
    @JoinColumn(name = "candidateId", referencedColumnName = "candidateId")
    @JsonIgnoreProperties("interviews")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancyId", referencedColumnName = "vacancyId")
    @JsonIgnoreProperties("interviews")
    private Vacancy vacancy;

    // Геттеры и сеттеры
}
