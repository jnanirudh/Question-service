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

// List all questions in the db
    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }
// Add more questions
    public String addQuestion(Question question) {
        questionDao.save(question);
        return "Success";
    }
// Delete question
    public ResponseEntity<String> deleteQuestion(Integer id) {
        if (questionDao.existsById(id)) {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

// Get all questions (view all subject questions)
    public List<Question> getQuestionsBySubject(String subject) {
        return questionDao.findBySubject(subject);
    }

// Get random questions (Quiz creation)
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String subject, Integer numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsBySubject(subject, numQ);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


// Convert Question IDs into QuestionWrappers (DTO) for the Student View
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        for (Integer id : questionIds) {
            questionDao.findById(id).ifPresent(question -> {
                QuestionWrapper wrapper = new QuestionWrapper(
                        question.getId(),
                        question.getQuestionTitle(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4()
                );
                wrappers.add(wrapper);
            });
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