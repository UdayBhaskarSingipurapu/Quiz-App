package com.microservices_quiz.QuizService.controller;

import com.microservices_quiz.QuizService.dto.ApiResponse;
import com.microservices_quiz.QuizService.dto.QuestionResponseDto;
import com.microservices_quiz.QuizService.dto.QuizRequestDto;
import com.microservices_quiz.QuizService.model.SubmitResponse;
import com.microservices_quiz.QuizService.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createQuiz(@Valid @RequestBody QuizRequestDto quizRequestDto){
        String result = quizService.createQuiz(quizRequestDto.getCategory(), quizRequestDto.getNumQ(), quizRequestDto.getTitle());
        ApiResponse<String> response;
        if(result.toLowerCase().contains("fallback")){
            response = new ApiResponse<>(HttpStatus.SERVICE_UNAVAILABLE, "Failed", result);
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response = new ApiResponse<>(HttpStatus.CREATED, "created", result);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ApiResponse<List<QuestionResponseDto>>> getQuizById(@PathVariable Long id){
        List<QuestionResponseDto> quizQuestions = quizService.getQuizQuestionsById(id);
        ApiResponse<List<QuestionResponseDto>> response;
        if(quizQuestions.isEmpty()){
            response = new ApiResponse<>(HttpStatus.SERVICE_UNAVAILABLE, "Failed to fetch, Question Service is temporarily unavailable", quizQuestions);
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response = new ApiResponse<>(HttpStatus.OK, "Fetched Data from DB", quizQuestions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    Calculate the score of user after submitting the quiz
    @PostMapping("/submit/{id}")
    public ResponseEntity<ApiResponse<Integer>> getQuizScore(@PathVariable Long id, @RequestBody List<SubmitResponse> submitResponses){
        Integer score = quizService.calculateQuizScore(id, submitResponses);
        ApiResponse<Integer> response;
        if(score == -1){
            response = new ApiResponse<>(HttpStatus.SERVICE_UNAVAILABLE, "Failed to fetch, Question Service is temporarily unavailable", score);
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response = new ApiResponse<>(HttpStatus.OK, "Score of user", score);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
