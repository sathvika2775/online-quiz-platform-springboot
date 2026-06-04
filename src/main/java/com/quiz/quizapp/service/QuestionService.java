package com.quiz.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository repo;

    public List<Question> getAllQuestions() {
        return repo.findAll();
    }

    public Question addQuestion(Question question) {
        return repo.save(question);
    }

    public List<Question> getQuestionsByCategory(String category) {
        return repo.findByCategory(category);
    }

    public List<Question> getQuestionsByDifficultyLevel(String difficultyLevel){
        return repo.findByDifficultyLevel(difficultyLevel);
    }
}