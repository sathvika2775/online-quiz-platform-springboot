package com.quiz.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.quizapp.model.QuestionWrapper;
import com.quiz.quizapp.model.Quiz;
import com.quiz.quizapp.model.Response;
import com.quiz.quizapp.model.Result;
import com.quiz.quizapp.service.QuizService;

@RestController
public class QuizController {

    @Autowired
    QuizService quizService;

    @GetMapping("/quiz/create")
    public Quiz createQuiz(@RequestParam String category,
                           @RequestParam Integer numQ) {
        return quizService.createQuiz(category, numQ);
    }

    @GetMapping("/quiz/{id}")
    public Quiz getQuiz(@PathVariable Integer id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("/quiz/submit/{id}")
    public Result submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }

    @GetMapping("/quiz/questions/{id}")
    public List<QuestionWrapper> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }
}