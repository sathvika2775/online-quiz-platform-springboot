package com.quiz.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.service.QuestionService;

@RestController
public class QuestionController {

    @Autowired
    QuestionService service;

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return service.getAllQuestions();
    }

    @PostMapping("/question")
    public Question addQuestion(@RequestBody Question question) {
        return service.addQuestion(question);
    }
}