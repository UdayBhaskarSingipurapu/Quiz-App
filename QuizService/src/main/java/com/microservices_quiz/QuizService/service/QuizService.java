package com.microservices_quiz.QuizService.service;

import com.microservices_quiz.QuizService.dto.QuestionResponseDto;
import com.microservices_quiz.QuizService.model.SubmitResponse;

import java.util.List;

public interface QuizService {
    String createQuiz(String category, int numQ, String title);
    List<QuestionResponseDto> getQuizQuestionsById(Long id);
    Integer calculateQuizScore(Long id, List<SubmitResponse> submitResponses);
}
