package com.example.questionservice.service;

import com.example.questionservice.dao.QuestionDao;
import com.example.questionservice.model.ApiResponse;
import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    // List all questions in the db
    public ResponseEntity<ApiResponse<List<Question>>> getAllQuestions() {
        List<Question> questions = questionDao.findAll();
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Questions fetched successfully", questions)
        );
    }

    // Add more questions
    public ResponseEntity<ApiResponse<String>> addQuestion(Question question) {
        questionDao.save(question);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("success", "Question added successfully", null));
    }

    // Delete question
    public ResponseEntity<ApiResponse<String>> deleteQuestion(Integer id) {
        if (questionDao.existsById(id)) {
            questionDao.deleteById(id);
            return ResponseEntity.ok(
                    new ApiResponse<>("success", "Deleted successfully", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", "Question ID " + id + " not found", null));
        }
    }

    // Get questions by subject
    public ResponseEntity<ApiResponse<List<Question>>> getQuestionsBySubject(String subject) {
        List<Question> questions = questionDao.findBySubject(subject);
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Questions for subject fetched", questions)
        );
    }

    // Get random question IDs for quiz creation
    public ResponseEntity<ApiResponse<List<Integer>>> getQuestionsForQuiz(String subject, Integer numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsBySubject(subject, numQ);
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Quiz question IDs ready", questions)
        );
    }

    // Convert IDs to QuestionWrappers (student view — no correct answer exposed)
    public ResponseEntity<ApiResponse<List<QuestionWrapper>>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        for (Integer id : questionIds) {
            questionDao.findById(id).ifPresent(question -> {
                wrappers.add(new QuestionWrapper(
                        question.getId(),
                        question.getQuestionTitle(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4()
                ));
            });
        }
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Question wrappers fetched", wrappers)
        );
    }

    // Calculate score
    public ResponseEntity<ApiResponse<Integer>> getScore(List<Response> responses) {
        int[] right = {0};

        for (Response response : responses) {
            questionDao.findById(response.getId()).ifPresent(question -> {
                if (response.getResponse().equals(question.getRightAnswer())) {
                    right[0]++;
                }
            });
        }
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Score calculated", right[0])
        );
    }
}