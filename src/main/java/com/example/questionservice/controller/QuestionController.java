package com.example.questionservice.controller;

import com.example.questionservice.model.Question;

import com.example.questionservice.service.QuestionService;
//import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping()
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }

//    @GetMapping("questionId")
//    public List<Question> getSingleQuestion(PathParam String questionId)
//    {
//        return questionService.getSingleQuestion(questionId);
//
//    }

    @PostMapping()
    public String addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }
}