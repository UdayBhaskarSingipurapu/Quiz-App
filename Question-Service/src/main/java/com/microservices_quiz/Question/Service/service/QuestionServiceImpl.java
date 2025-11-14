package com.microservices_quiz.Question.Service.service;

import com.microservices_quiz.Question.Service.dto.QuestionRequestDto;
import com.microservices_quiz.Question.Service.dto.QuestionResponseDto;
import com.microservices_quiz.Question.Service.exception.ResourceNotFoundException;
import com.microservices_quiz.Question.Service.model.Question;
import com.microservices_quiz.Question.Service.model.SubmitResponse;
import com.microservices_quiz.Question.Service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;


    @Override
    public List<QuestionResponseDto> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionResponseDto> getQuestionsByCategory(String category){
        return questionRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionResponseDto addQuestion(QuestionRequestDto question) {
        Question ques = convertToEntity(question);
        Question saved = questionRepository.save(ques);
        return convertToDTO(saved);
    }

    @Override
    public QuestionResponseDto updateQuestion(Long id, QuestionRequestDto updatedQuestion) {
        Question existingQuestion = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question with id " + id + " not found"));
        existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
        existingQuestion.setOption1(updatedQuestion.getOption1());
        existingQuestion.setOption2(updatedQuestion.getOption2());
        existingQuestion.setOption3(updatedQuestion.getOption3());
        existingQuestion.setOption3(updatedQuestion.getOption3());
        existingQuestion.setOption4(updatedQuestion.getOption4());
        existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
        existingQuestion.setCategory(updatedQuestion.getCategory());
        existingQuestion.setDifficultylevel(updatedQuestion.getDifficultylevel());
        Question updated = questionRepository.save(existingQuestion);
        QuestionResponseDto response = convertToDTO(updated);
        return response;
    }

    @Override
    public String deleteQuestion(Long id) {
        if(questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return "Question deleted successfully!";
        } else {
            return "Question with ID " + id + " not found!";
        }
    }

    @Override
    public List<Long> createQuizQuestions(String category, Integer numQ) {
        List<Long> questions = questionRepository.findQuestionsByCategoryAndCount(category, numQ);
        if(questions.isEmpty()){
            throw new ResourceNotFoundException("No questions found with category " + category + " exists");
        }
        return questions;
    }

    @Override
    public List<QuestionResponseDto> getQuestionsByQuizId(List<Long> ids) {
        List<Question> questions = questionRepository.findAllById(ids);
        return questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Integer calculateScoreForQuiz(List<SubmitResponse> responses) {
        int score = 0;
        for(SubmitResponse response: responses){
            Question ques = questionRepository.findById(response.getId()).get();
            if(ques.getRightAnswer().equals(response.getResponse())){
                score++;
            }
        }
        return score;
    }

    private QuestionResponseDto convertToDTO(Question q) {
        QuestionResponseDto dto = new QuestionResponseDto();
        dto.setId(q.getId());
        dto.setQuestionTitle(q.getQuestionTitle());
        dto.setOption1(q.getOption1());
        dto.setOption2(q.getOption2());
        dto.setOption3(q.getOption3());
        dto.setOption4(q.getOption4());
        return dto;
    }

    private Question convertToEntity(QuestionRequestDto dto) {
        Question q = new Question();
        q.setQuestionTitle(dto.getQuestionTitle());
        q.setOption1(dto.getOption1());
        q.setOption2(dto.getOption2());
        q.setOption3(dto.getOption3());
        q.setOption4(dto.getOption4());
        q.setRightAnswer(dto.getRightAnswer());
        q.setCategory(dto.getCategory());
        q.setDifficultylevel(dto.getDifficultylevel());
        return q;
    }
}
