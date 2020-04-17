package com.smzn.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView tHighScores;

    private static final int REQUEST_ID = 1;
    private int highscores;

    public static final String MySharedPref = "sharedPref";
    public static final String QuizHighscores = "myHighScores";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_start_quiz);

        tHighScores = findViewById(R.id.tvHighScores);
        loadHighScores();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,QuizActivity.class);
                startActivityForResult(intent,REQUEST_ID);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == REQUEST_ID){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORES,0);
                if(score > highscores){
                    updateHighestScores(score);
                }
            }

        }
    }




    private void loadHighScores(){
        SharedPreferences preferences = getSharedPreferences(MySharedPref,MODE_PRIVATE);
        highscores = preferences.getInt(QuizHighscores,0);
        tHighScores.setText("High Scores : "+highscores);
        //Log.e("Mizan2",String.valueOf(highscores));
    }



    private void updateHighestScores(int newHighScores){
        highscores = newHighScores;
        tHighScores.setText("Highscore : "+highscores);

        SharedPreferences sPref = getSharedPreferences(MySharedPref,MODE_PRIVATE);
        SharedPreferences.Editor editor =sPref.edit();
        editor.putInt(QuizHighscores,highscores);
        editor.apply();

    }




}
