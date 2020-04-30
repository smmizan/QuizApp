package com.smmizan.quizapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Questions implements Parcelable {


    public static final String DIFFICULTY_EASY = "easy";
    public static final String DIFFICULTY_MEDIUM = "medium";
    public static final String DIFFICULTY_HARD = "hard";

    private int id;
    private String questionName;
    private String question1;
    private String question2;
    private String question3;
    private int queestionNumber;
    private String difficultLavel;
    private int categoriesID;

    public Questions(String questionName, String question1, String question2, String question3, int queestionNumber,String difficultLavel,int categoriesID) {
        this.questionName = questionName;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.queestionNumber = queestionNumber;
        this.difficultLavel=difficultLavel;
        this.categoriesID=categoriesID;
    }


    public Questions()
    {}

    protected Questions(Parcel in) {

        id = in.readInt();
        questionName = in.readString();
        question1 = in.readString();
        question2 = in.readString();
        question3 = in.readString();
        queestionNumber = in.readInt();
        difficultLavel = in.readString();
        categoriesID = in.readInt();
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getCategoriesID() {
        return categoriesID;
    }

    public void setCategoriesID(int categoriesID) {
        this.categoriesID = categoriesID;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(questionName);
        dest.writeString(question1);
        dest.writeString(question2);
        dest.writeString(question3);
        dest.writeInt(queestionNumber);
        dest.writeString(difficultLavel);
        dest.writeInt(categoriesID);
    }


    public String getDifficultLavel() {
        return difficultLavel;
    }

    public void setDifficultLavel(String difficultLavel) {
        this.difficultLavel = difficultLavel;
    }



    public static String[] getDifficultLavels(){
        return new String[]{
                DIFFICULTY_EASY,
                DIFFICULTY_MEDIUM,
                DIFFICULTY_HARD
        };
    }

}
