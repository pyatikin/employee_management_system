package com.pyatkin.is.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * todo Document type InterviewData
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewData {
    private Integer interviewEvaluation;
    private Integer conversationEvaluation;

    private Integer recommendationEvaluation;
    private String comment;
}
