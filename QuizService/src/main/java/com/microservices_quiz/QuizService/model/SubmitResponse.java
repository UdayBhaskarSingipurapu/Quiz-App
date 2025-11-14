package com.microservices_quiz.QuizService.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SubmitResponse {
    private Long id;
    private String response;
}
