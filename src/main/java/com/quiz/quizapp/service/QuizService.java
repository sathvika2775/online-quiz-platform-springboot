package com.quiz.quizapp.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.model.Quiz;
import com.quiz.quizapp.model.Response;
import com.quiz.quizapp.repository.QuestionRepository;
import com.quiz.quizapp.repository.QuizRepository;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepo;
    @Autowired
    QuestionRepository questionRepo;
    public Quiz createQuiz(String category, Integer numQ) {
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(category + " Quiz");
        quiz.setQuestions(questions);
        return quizRepo.save(quiz);
    }

    public Quiz getQuiz(Integer id) {
        return quizRepo.findById(id).orElse(null);
    }
    public Integer calculateResult(Integer id, List<Response> responses) {

        Quiz quiz = quizRepo.findById(id).orElse(null);
        int score = 0;
        int i = 0;
        for (Question question : quiz.getQuestions()) {
            if (question.getRightAnswer().equals(responses.get(i).getResponse())) {
                score++;
            }
            i++;
        }

        return score;
    }
}