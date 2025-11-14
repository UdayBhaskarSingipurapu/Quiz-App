package com.microservices_quiz.QuizService.service;

import com.microservices_quiz.QuizService.dto.ApiResponse;
import com.microservices_quiz.QuizService.dto.QuestionResponseDto;
import com.microservices_quiz.QuizService.exceptions.ResourceNotFoundException;
import com.microservices_quiz.QuizService.feignclients.QuestionFeign;
import com.microservices_quiz.QuizService.model.Quiz;
import com.microservices_quiz.QuizService.model.SubmitResponse;
import com.microservices_quiz.QuizService.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionFeign questionFeign;

    @Override
    public String createQuiz(String category, int numQ, String title) {
        ResponseEntity<Object> response = questionFeign.generateQuestionsForQuiz(category, numQ);

        // Convert JSON manually
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        List<Long> questions = (List<Long>) responseBody.get("data");

        if(questions == null || questions.isEmpty()) {
            throw new ResourceNotFoundException("No questions found for category: " + category);
        }

        Quiz quiz = new Quiz();
        quiz.setQuizTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return "Quiz created successfully with " + questions.size() + " questions";
    }

    @Override
    public List<QuestionResponseDto> getQuizQuestionsById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz with id " + id + " does not exist"));

        List<Long> questionIds = quiz.getQuestions();
        if(questionIds == null || questionIds.isEmpty()) {
            throw new ResourceNotFoundException("No questions found for quiz id " + id);
        }

        // Feign call returning raw Object
        ResponseEntity<Object> response = questionFeign.getQuestionsById(questionIds);
        // Manual extraction (same as you did before)
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        List<Map<String, Object>> questionData = (List<Map<String, Object>>) responseBody.get("data");

        if(questionData == null || questionData.isEmpty()) {
            throw new ResourceNotFoundException("No questions returned from Question Service for quiz id " + id);
        }

        // Convert Map -> DTO manually
        List<QuestionResponseDto> questions = questionData.stream().map(map -> {
            QuestionResponseDto dto = new QuestionResponseDto();
            dto.setId(((Number) map.get("id")).longValue());
            dto.setQuestionTitle((String) map.get("questionTitle"));
            dto.setOption1((String) map.get("option1"));
            dto.setOption2((String) map.get("option2"));
            dto.setOption3((String) map.get("option3"));
            dto.setOption4((String) map.get("option4"));
            return dto;
        }).collect(Collectors.toList());

        return questions;
    }


    @Override
    public Integer calculateQuizScore(Long id, List<SubmitResponse> submitResponses) {
        ResponseEntity<Object> response = questionFeign.getScoreForQuiz(submitResponses);

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        Integer score = (Integer) responseBody.get("data");

        if(score == null) {
            throw new RuntimeException("Failed to fetch quiz score from Question Service");
        }

        return score;
    }

}
