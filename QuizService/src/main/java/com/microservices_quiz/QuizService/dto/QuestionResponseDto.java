package com.microservices_quiz.QuizService.dto;

import lombok.Data;

@Data
public class QuestionResponseDto {
    private Long id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
