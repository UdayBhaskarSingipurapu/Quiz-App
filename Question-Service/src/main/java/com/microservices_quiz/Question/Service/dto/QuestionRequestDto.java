package com.microservices_quiz.Question.Service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionRequestDto {
    @NotBlank(message = "Question title cannot be blank")
    private String questionTitle;

    @NotBlank(message = "Options cannot be empty")
    private String option1;
    @NotBlank(message = "Options cannot be empty")
    private String option2;
    @NotBlank(message = "Options cannot be empty")
    private String option3;
    @NotBlank(message = "Options cannot be empty")
    private String option4;

    @NotBlank(message = "Right answer cannot be empty")
    private String rightAnswer;

    @NotBlank(message = "Difficulty level cannot be negative")
    private String difficultylevel;

    @NotBlank(message = "Category cannot be negative")
    private String category;
}
