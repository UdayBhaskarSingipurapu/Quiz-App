package com.microservices_quiz.QuizService.feignclients;

import com.microservices_quiz.QuizService.dto.ApiResponse;
import com.microservices_quiz.QuizService.dto.QuestionResponseDto;
import com.microservices_quiz.QuizService.model.SubmitResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@FeignClient("QUESTION-SERVICE")
public interface QuestionFeign {

    //    Generate Quiz
    @GetMapping("/question/generateQuiz")
    public ResponseEntity<Object> generateQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQ);

    //    Get questions for quiz
    @PostMapping("/question/getQuestions")
    public ResponseEntity<Object> getQuestionsById(@RequestBody List<Long> ids);

    //    Calculate Score
    @PostMapping("/question/getScore")
    public ResponseEntity<Object> getScoreForQuiz(@RequestBody List<SubmitResponse> responses);
}
