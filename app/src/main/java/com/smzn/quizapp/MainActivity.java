package com.smzn.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.smzn.quizapp.database.MyDatabaseHelper;
import com.smzn.quizapp.model.Categories;
import com.smzn.quizapp.model.Questions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView tHighScores;

    private static final int REQUEST_ID = 1;
    private int highscores;

    public static final String MySharedPref = "sharedPref";
    public static final String QuizHighscores = "myHighScores";

    private Spinner spinner,spinnnerCategories;

    public static final String DIFFICULTY_LAVEL = "difficultLavel";
    public static final String CATEGORY_ID = "categoryID";
    public static final String CATEGORY_NAME = "categoryName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_start_quiz);
        spinner = findViewById(R.id.spinner);
        spinnnerCategories = findViewById(R.id.spinnerCategories);

        tHighScores = findViewById(R.id.tvHighScores);


        loadDifficultyLavel();
        loadCategories();

        loadHighScores();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String QuizLavel = spinner.getSelectedItem().toString();

                Categories categoriesSelected =(Categories) spinnnerCategories.getSelectedItem();
                int categoryID = categoriesSelected.getId();
                String categoryName = categoriesSelected.getName();





                Intent intent =new Intent(MainActivity.this,QuizActivity.class);
                intent.putExtra(DIFFICULTY_LAVEL,QuizLavel);
                intent.putExtra(CATEGORY_ID,categoryID);
                intent.putExtra(CATEGORY_NAME,categoryName);
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



    private void loadDifficultyLavel(){
        String[] spinnerDifficultLavel  = Questions.getDifficultLavels();

        ArrayAdapter<String> spinnnerArray = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,spinnerDifficultLavel);

        spinnnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnnerArray);
    }

    private void loadCategories(){
        MyDatabaseHelper myDatabaseHelper = MyDatabaseHelper.getInstance(this);
        List<Categories> categoriesList = myDatabaseHelper.getAllCategories();

        ArrayAdapter<Categories> spinnerCategoriesArray = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,categoriesList);
        spinnerCategoriesArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnnerCategories.setAdapter(spinnerCategoriesArray);

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
