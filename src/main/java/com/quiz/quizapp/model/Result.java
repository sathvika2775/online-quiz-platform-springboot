package com.quiz.quizapp.model;
public class Result {

    private Integer score;
    private Integer totalQuestions;

    public Result() {
    }

    public Result(Integer score, Integer totalQuestions) {
        this.score = score;
        this.totalQuestions = totalQuestions;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}