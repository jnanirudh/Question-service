package com.example.questionservice.controller;

import com.example.questionservice.model.ApiResponse;
import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;
import com.example.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    // GET /question/allQuestions
    @GetMapping("/allQuestions")
    public ResponseEntity<ApiResponse<List<Question>>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // POST /question/add
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addQuestion(
            @RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // DELETE /question/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQuestion(
            @PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

    // GET /question/subject/{subject}
    @GetMapping("/subject/{subject}")
    public ResponseEntity<ApiResponse<List<Question>>> getQuestionsBySubject(
            @PathVariable String subject) {
        return questionService.getQuestionsBySubject(subject);
    }

    // GET /question/generate?subject=Java&numQ=5
    @GetMapping("/generate")
    public ResponseEntity<ApiResponse<List<Integer>>> getQuestionsForQuiz(
            @RequestParam String subject,
            @RequestParam Integer numQ) {
        return questionService.getQuestionsForQuiz(subject, numQ);
    }

    // POST /question/getQuestions
    @PostMapping("/getQuestions")
    public ResponseEntity<ApiResponse<List<QuestionWrapper>>> getQuestionsFromId(
            @RequestBody List<Integer> questionIds) {
        return questionService.getQuestionsFromId(questionIds);
    }

    // POST /question/getScore
    @PostMapping("/getScore")
    public ResponseEntity<ApiResponse<Integer>> getScore(
            @RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }
}