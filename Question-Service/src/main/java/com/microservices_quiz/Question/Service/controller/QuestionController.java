package com.microservices_quiz.Question.Service.controller;


import com.microservices_quiz.Question.Service.dto.ApiResponse;
import com.microservices_quiz.Question.Service.dto.QuestionRequestDto;
import com.microservices_quiz.Question.Service.dto.QuestionResponseDto;
import com.microservices_quiz.Question.Service.model.SubmitResponse;
import com.microservices_quiz.Question.Service.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
//
//    @Autowired
//    Environment environment;

    @GetMapping("")
    public ResponseEntity<ApiResponse<String>> welcome() {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK, "Welcome to Quiz App", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<QuestionResponseDto>>> getAllQuestions() {
        List<QuestionResponseDto> data = questionService.getAllQuestions();
        ApiResponse<List<QuestionResponseDto>> response = new ApiResponse<>(HttpStatus.OK, "Fetched all questions", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<QuestionResponseDto>>> getByCategory(@PathVariable String category) {
        List<QuestionResponseDto> data = questionService.getQuestionsByCategory(category);
        ApiResponse<List<QuestionResponseDto>> response = new ApiResponse<>(HttpStatus.OK, "Fetched questions by category", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<QuestionResponseDto>> addQuestion(@Valid @RequestBody QuestionRequestDto question) {
        QuestionResponseDto created = questionService.addQuestion(question);
        ApiResponse<QuestionResponseDto> response = new ApiResponse<>(HttpStatus.CREATED, "Question added successfully", created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<QuestionResponseDto>> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequestDto question) {
        QuestionResponseDto updated = questionService.updateQuestion(id, question);
        ApiResponse<QuestionResponseDto> response = new ApiResponse<>(HttpStatus.OK, "Question updated successfully", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQuestion(@PathVariable Long id) {
        String result = questionService.deleteQuestion(id);
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK, result, null);
        return ResponseEntity.ok(response);
    }

//    Generate Quiz
    @GetMapping("/generateQuiz")
    public ResponseEntity<ApiResponse<List<Long>>> generateQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQ){
        List<Long> questionIds = questionService.createQuizQuestions(category, numQ);
//        System.out.println("Returning data from question service " + questionIds.toString());
        ApiResponse<List<Long>> response = new ApiResponse<>(HttpStatus.CREATED, "Quiz created successfully", questionIds);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
//    Get questions for quiz
    @PostMapping("getQuestions")
    public ResponseEntity<ApiResponse<List<QuestionResponseDto>>> getQuestionsById(@RequestBody List<Long> ids){
//        System.out.println(environment.getProperty("local.server.port"));
        List<QuestionResponseDto> questions = questionService.getQuestionsByQuizId(ids);
        ApiResponse<List<QuestionResponseDto>> response = new ApiResponse<>(HttpStatus.OK, "Fecthed questions from DB", questions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//    Calculate Score
    @PostMapping("/getScore")
    public ResponseEntity<ApiResponse<Integer>> getScoreForQuiz(@RequestBody List<SubmitResponse> responses){
        Integer score = questionService.calculateScoreForQuiz(responses);
        ApiResponse<Integer> response = new ApiResponse<>(HttpStatus.OK, "Calculated Score for quiz", score);
//        System.out.println("returning score " + score);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

