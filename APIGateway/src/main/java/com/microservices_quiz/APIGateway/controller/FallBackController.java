package com.microservices_quiz.APIGateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/quizFallback")
    public ResponseEntity<String> quizFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Quiz service is temporarily unavailable.");
    }

    @GetMapping("/questionFallback")
    public ResponseEntity<String> questionFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Question service is temporarily unavailable.");
    }
}
