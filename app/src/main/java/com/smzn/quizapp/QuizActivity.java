package com.smzn.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.smzn.quizapp.database.MyDatabaseHelper;
import com.smzn.quizapp.model.Questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView tQuizScores,tQuizOfNumber,tQuizRemainingTime,tQuizQuestion;
    RadioGroup radioQuestionGroup;
    RadioButton radioButton1,radioButton2,radioButton3;
    Button bSubmit;

    private List<Questions> questionsArrayList;

    private ColorStateList colorStateList;
    private int questionCounter;
    private int questionCountTotal;
    private Questions currentQuestion;
    private int quizScores;
    private boolean quizAnswerd;


    public static final String EXTRA_SCORES = "extraScores";

    private long onbreakpresstimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        tQuizScores = findViewById(R.id.tv_score_board);
        tQuizOfNumber = findViewById(R.id.tv_number_of_question);
        tQuizRemainingTime = findViewById(R.id.tv_time_remaining);
        tQuizQuestion = findViewById(R.id.tv_question);

        radioQuestionGroup = findViewById(R.id.button_group);

        radioButton1 = findViewById(R.id.button_option_1);
        radioButton2 = findViewById(R.id.button_option_2);
        radioButton3 = findViewById(R.id.button_option_3);


        bSubmit = findViewById(R.id.button_submit_next);


        colorStateList = radioButton1.getTextColors();

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        questionsArrayList = myDatabaseHelper.getAllQuestions();

        questionCountTotal = questionsArrayList.size();
        Collections.shuffle(questionsArrayList);

        showNextQuestions();


        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!quizAnswerd){
                        if(radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked()){

                            cheeckedAnswer();

                        }else
                        {
                            Toast.makeText(QuizActivity.this, "Please answer any one options!", Toast.LENGTH_SHORT).show();
                        }

                }else {
                    showNextQuestions();
                }
            }
        });


    }



    private void showNextQuestions(){
        radioButton1.setTextColor(colorStateList);
        radioButton2.setTextColor(colorStateList);
        radioButton3.setTextColor(colorStateList);

        radioQuestionGroup.clearCheck();


        if(questionCounter < questionCountTotal){
            currentQuestion = questionsArrayList.get(questionCounter);

            tQuizQuestion.setText(currentQuestion.getQuestionName());
            radioButton1.setText(currentQuestion.getQuestion1());
            radioButton2.setText(currentQuestion.getQuestion2());
            radioButton3.setText(currentQuestion.getQuestion3());

            questionCounter++;
            tQuizOfNumber.setText("Question : "+ questionCounter + "/" + questionCountTotal);
            quizAnswerd = false;
            bSubmit.setText("Confirm");


        }else {
            quizFinished();
        }


    }


    private void quizFinished(){

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORES,quizScores);
        setResult(RESULT_OK,resultIntent);
        //Log.e("mizan","scores is "+quizScores);
        finish();
    }




    private void cheeckedAnswer(){
        quizAnswerd = true;

        RadioButton radioButtonSelected = findViewById(radioQuestionGroup.getCheckedRadioButtonId());
        int answerNumber = radioQuestionGroup.indexOfChild(radioButtonSelected) + 1;

        if(answerNumber == currentQuestion.getQueestionNumber()){
            quizScores++;
            tQuizScores.setText("Scores: "+ quizScores);
        }


        showSolution();

    }


    private void showSolution(){

        radioButton1.setTextColor(Color.RED);
        radioButton2.setTextColor(Color.RED);
        radioButton3.setTextColor(Color.RED);


        switch (currentQuestion.getQueestionNumber()){
            case 1:
                radioButton1.setTextColor(Color.GREEN);
                tQuizQuestion.setText("Answer 1 is correct");
                break;

            case 2:
                radioButton2.setTextColor(Color.GREEN);
                tQuizQuestion.setText("Answer 2 is correct");
                break;

            case 3:
                radioButton3.setTextColor(Color.GREEN);
                tQuizQuestion.setText("Answer 3 is correct");
                break;
        }

        if (questionCounter < questionCountTotal){
            bSubmit.setText("Next");
        }else {
            bSubmit.setText("Finished");
        }

    }


    @Override
    public void onBackPressed() {

        if(onbreakpresstimes + 2000 > System.currentTimeMillis()){
            quizFinished();
        }else {
            Toast.makeText(this, "press back again to close app !", Toast.LENGTH_SHORT).show();
        }


        onbreakpresstimes = System.currentTimeMillis();
    }
}
