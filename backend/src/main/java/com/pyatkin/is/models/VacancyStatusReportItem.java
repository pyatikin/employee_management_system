package com.pyatkin.is.models;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class VacancyStatusReportItem {
    private String stage;
    private Long count;
    private double relativeCount;

    // конструктор, геттеры и сеттеры
}