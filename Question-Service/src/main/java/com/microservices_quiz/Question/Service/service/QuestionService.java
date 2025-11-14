package com.microservices_quiz.Question.Service.service;


import com.microservices_quiz.Question.Service.dto.QuestionRequestDto;
import com.microservices_quiz.Question.Service.dto.QuestionResponseDto;
import com.microservices_quiz.Question.Service.model.SubmitResponse;

import java.util.List;

public interface QuestionService {

    List<QuestionResponseDto> getAllQuestions();
    List<QuestionResponseDto> getQuestionsByCategory(String category);
    QuestionResponseDto addQuestion(QuestionRequestDto question);
    QuestionResponseDto updateQuestion(Long id, QuestionRequestDto updatedQuestion);
    String deleteQuestion(Long id);
    List<Long> createQuizQuestions(String category, Integer numQ);
    List<QuestionResponseDto> getQuestionsByQuizId(List<Long> id);
    Integer calculateScoreForQuiz(List<SubmitResponse> responses);
}
