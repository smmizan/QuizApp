package com.smzn.quizapp.model;

public class Questions {

    private String questionName;
    private String question1;
    private String question2;
    private String question3;
    private int queestionNumber;

    public Questions(String questionName, String question1, String question2, String question3, int queestionNumber) {
        this.questionName = questionName;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.queestionNumber = queestionNumber;
    }


    public Questions()
    {}

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public int getQueestionNumber() {
        return queestionNumber;
    }

    public void setQueestionNumber(int queestionNumber) {
        this.queestionNumber = queestionNumber;
    }
}
