package com.quiz.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.quizapp.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}