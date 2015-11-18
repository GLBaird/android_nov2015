package com.leonbaird.scifiquiz;

public class Question {

    public String questionText;
    public String answer1;
    public String answer2;
    public String answer3;
    public int correctAnswer;

    public Question(String qt, String a1, String a2, String a3, int ca) {
        questionText = qt;
        answer1 = a1;
        answer2 = a2;
        answer3 = a3;
        correctAnswer = ca;
    }

    public boolean isCorrectAnswer(int answer) {
        return correctAnswer == answer;
    }

}
