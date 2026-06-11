package com.quiz.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.quizapp.exception.QuizNotFoundException;
import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.model.QuestionWrapper;
import com.quiz.quizapp.model.Quiz;
import com.quiz.quizapp.model.Response;
import com.quiz.quizapp.model.Result;
import com.quiz.quizapp.repository.QuestionRepository;
import com.quiz.quizapp.repository.QuizRepository;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepo;

    @Autowired
    QuestionRepository questionRepo;

    public Quiz createQuiz(String category, Integer numQ) {

        if (numQ <= 0) {
            throw new QuizNotFoundException(
                    "Number of questions must be greater than 0");
        }

        if (numQ > 20) {
            throw new QuizNotFoundException(
                    "Maximum 20 questions allowed");
        }

        List<Question> questions =
                questionRepo.findRandomQuestionsByCategory(category, numQ);

        if (questions.isEmpty()) {
            throw new QuizNotFoundException("Category not found");
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(category + " Quiz");
        quiz.setQuestions(questions);

        return quizRepo.save(quiz);
    }

    public Quiz getQuiz(Integer id) {

        return quizRepo.findById(id)
                .orElseThrow(() ->
                        new QuizNotFoundException("Quiz not found"));
    }

    public Result calculateResult(Integer id, List<Response> responses) {

        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() ->
                        new QuizNotFoundException("Quiz not found"));

        if (responses.size() != quiz.getQuestions().size()) {
            throw new QuizNotFoundException(
                    "Number of responses does not match quiz questions");
        }

        int score = 0;

        for (int i = 0; i < quiz.getQuestions().size(); i++) {

            Question question = quiz.getQuestions().get(i);

            if (question.getRightAnswer()
                    .equals(responses.get(i).getResponse())) {

                score++;
            }
        }

        return new Result(score, quiz.getQuestions().size());
    }

    public List<QuestionWrapper> getQuizQuestions(Integer id) {

        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() ->
                        new QuizNotFoundException("Quiz not found"));

        List<QuestionWrapper> questionWrappers = new ArrayList<>();

        for (Question q : quiz.getQuestions()) {

            QuestionWrapper qw = new QuestionWrapper(
                    q.getId(),
                    q.getQuestionTitle(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4());

            questionWrappers.add(qw);
        }

        return questionWrappers;
    }
}