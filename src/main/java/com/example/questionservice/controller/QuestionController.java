package com.example.questionservice.controller;

import com.example.questionservice.model.Question;
import com.example.questionservice.model.Response;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.service.QuestionService;
//import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    // Add a new question to MySQL
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        String response = questionService.addQuestion(question); // Gets "Success"
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String subject, @RequestParam Integer numQ) {
        return questionService.getQuestionsForQuiz(subject, numQ);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

// Get QuestionWrappers (DTOs) for the student view
    @PostMapping("getQuestionsFromId")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        return questionService.getQuestionsFromId(questionIds);
    }

// For Report Service - Calculate the final score
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }
}