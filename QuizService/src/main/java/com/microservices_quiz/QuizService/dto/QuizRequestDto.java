package com.microservices_quiz.QuizService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizRequestDto {
    @NotBlank(message = "Quiz category is Required")
    String category;

    @NotNull(message = "Number of questions is required")
    Integer numQ;

    @NotBlank(message = "title is required")
    String title;
}
