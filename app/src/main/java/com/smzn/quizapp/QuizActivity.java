package com.smzn.quizapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.smzn.quizapp.database.MyDatabaseHelper;
import com.smzn.quizapp.model.Questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    TextView tQuizScores,tQuizOfNumber,tQuizRemainingTime,tQuizQuestion;
    RadioGroup radioQuestionGroup;
    RadioButton radioButton1,radioButton2,radioButton3;
    Button bSubmit;

    private ArrayList<Questions> questionsArrayList;

    private ColorStateList colorStateList;
    private int questionCounter;
    private int questionCountTotal;
    private Questions currentQuestion;
    private int quizScores;
    private boolean quizAnswerd;


    public static final String EXTRA_SCORES = "extraScores";


    //count down

    private static final long COUNTDOWN_TIMER = 30000;
    private ColorStateList colorStateListCountDown;
    private CountDownTimer countDownTimer;
    private long timeLeftinMillies;


    private long onbreakpresstimes;


    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_TIME_REMAINING = "keyTimeRemaining";
    private static final String KEY_QUESTIONS = "keyQestion";
    private static final String KEY_ANSWERED = "keyAnsered";


    TextView tCategory;


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

        tCategory = findViewById(R.id.tv_quiz_category);


        colorStateList = radioButton1.getTextColors();

        colorStateListCountDown = tQuizRemainingTime.getTextColors();


        Intent intent = getIntent();
        String lavel = intent.getStringExtra(MainActivity.DIFFICULTY_LAVEL);
        int categoryID = intent.getIntExtra(MainActivity.CATEGORY_ID,0);
        String categoryName  = intent.getStringExtra(MainActivity.CATEGORY_NAME);


        tCategory.setText("Category : "+categoryName);

        getSupportActionBar().setTitle("Lavel : "+lavel);

        if(savedInstanceState==null){

            MyDatabaseHelper myDatabaseHelper = MyDatabaseHelper.getInstance(this);
            questionsArrayList = myDatabaseHelper.getQuestions(categoryID,lavel);

            questionCountTotal = questionsArrayList.size();
            Collections.shuffle(questionsArrayList);

            showNextQuestions();

        }else
        {
            questionsArrayList = savedInstanceState.getParcelableArrayList(KEY_QUESTIONS);
            questionCountTotal = questionsArrayList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionsArrayList.get(questionCounter - 1);
            quizScores = savedInstanceState.getInt(KEY_SCORE);
            timeLeftinMillies = savedInstanceState.getLong(KEY_TIME_REMAINING);
            quizAnswerd = savedInstanceState.getBoolean(KEY_ANSWERED);


            if(!quizAnswerd){
                startCountDownTimer();
            }else{
                updateCountDownText();
                showSolution();
            }

        }




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




            timeLeftinMillies = COUNTDOWN_TIMER;
            startCountDownTimer();


        }else {
            quizFinished();
        }


    }



    private void startCountDownTimer(){
        countDownTimer = new CountDownTimer(timeLeftinMillies,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMillies = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {

                timeLeftinMillies = 0;
                updateCountDownText();
                cheeckedAnswer();

            }
        }.start();


    }


    private void updateCountDownText(){

        int minutes = (int) (timeLeftinMillies / 1000) / 60;
        int seconds = (int) (timeLeftinMillies /1000) % 60;

        String countdownTimeFormate = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        tQuizRemainingTime.setText(countdownTimeFormate);


        if(timeLeftinMillies <= 10000){
            tQuizRemainingTime.setTextColor(Color.RED);
        }else {
            tQuizRemainingTime.setTextColor(colorStateListCountDown);
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


        countDownTimer.cancel();

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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE,quizScores);
        outState.putInt(KEY_QUESTION_COUNT,questionCountTotal);
        outState.putLong(KEY_TIME_REMAINING,timeLeftinMillies);
        outState.putBoolean(KEY_ANSWERED,quizAnswerd);
        outState.putParcelableArrayList(KEY_QUESTIONS,questionsArrayList);
    }
}
