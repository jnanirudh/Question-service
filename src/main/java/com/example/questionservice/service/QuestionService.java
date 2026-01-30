package com.example.questionservice.service;

import com.example.questionservice.dao.QuestionDao;
import com.example.questionservice.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "Success";
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questionDao.findBySubject(subject);
    }
}