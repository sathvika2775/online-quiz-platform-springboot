
package com.quiz.quizapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quiz.quizapp.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query(value =
    "SELECT * FROM question WHERE category = :category ORDER BY RAND() LIMIT :numQ",
    nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(@Param("category") String category,@Param("numQ") Integer numQ);
    List<Question> findByCategory(String category);

    List<Question> findTop5ByCategory(String category);
    List<Question> findByDifficultyLevel(String difficultyLevel);
}