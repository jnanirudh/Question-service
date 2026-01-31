package com.example.questionservice.service;

import com.example.questionservice.dao.QuestionDao;
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

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "Success";
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questionDao.findBySubject(subject);
    }


// get random IDs for the Quiz Service
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String subject, Integer numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsBySubject(subject, numQ);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

// Convert Question IDs into QuestionWrappers (DTO) for the Student View
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        for (Integer id : questionIds) {
            Question question = questionDao.findById(id).get();
            // Create the DTO manually using the Question data
            QuestionWrapper wrapper = new QuestionWrapper(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            );
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

// Calculate the score
    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if (response.getResponse().equals(question.getRightAnswer())) {
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}