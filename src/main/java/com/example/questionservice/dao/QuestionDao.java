package com.example.questionservice.dao;

import com.example.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    // Find all questions by subject
    List<Question> findBySubject(String subject);

    // Custom query for the Quiz Service
    // Get random question IDs based on subject and a limit
    @Query(value = "SELECT q.id FROM question q WHERE q.subject=:subject ORDER BY RAND() LIMIT :numL", nativeQuery = true)
    List<Integer> findRandomQuestionsBySubject(String subject, int numL);
}