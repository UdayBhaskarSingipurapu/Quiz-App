package com.microservices_quiz.Question.Service.repository;

import com.microservices_quiz.Question.Service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    public List<Question> findByCategory(String category);

    @Query(value = "select q.id from question q where q.category=:category order by rand() limit :numQ", nativeQuery = true)
    List<Long> findQuestionsByCategoryAndCount(String category, int numQ);
}
