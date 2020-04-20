package com.smzn.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.smzn.quizapp.Contract.*;
import com.smzn.quizapp.model.Questions;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="super_quiz.db";
    private static final int DATABASE_VERSION = 3;

    //Context context;


    private SQLiteDatabase db;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;

        final String CEATE_QUIZ_QUESTIONS_TABLE = "CREATE TABLE "+
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION_1 + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION_2 + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION_3 + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION_ANSWER + " INTEGER" +
                " ) ";
        db.execSQL(CEATE_QUIZ_QUESTIONS_TABLE);

        quizQuestionsBank();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ QuestionsTable.TABLE_NAME);
        onCreate(db);

        Log.e("mizan","on ugrade");

    }


    private void quizQuestionsBank(){
        Questions questions1 = new Questions("A is correct","A","B","C",1);
        addQuestions(questions1);

        Questions questions2 = new Questions("B is correct","A","B","C",2);
        addQuestions(questions2);

        Questions questions3 = new Questions("C is correct","A","B","C",3);
        addQuestions(questions3);

        Questions questions4 = new Questions("B is correct","A","B","C",2);
        addQuestions(questions4);

        Questions questions5 = new Questions("C is correct","A","B","C",3);
        addQuestions(questions5);
    }


    private void addQuestions(Questions questions){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionsTable.COLUMN_QUESTION,questions.getQuestionName());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_1,questions.getQuestion1());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_2,questions.getQuestion2());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_3,questions.getQuestion3());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_ANSWER,questions.getQueestionNumber());
        db.insert(QuestionsTable.TABLE_NAME,null,contentValues);
    }



    public ArrayList<Questions> getAllQuestions(){
        ArrayList<Questions> questionsList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME,null);

        if (cursor.moveToFirst()){
            do {

                Questions questions = new Questions();
                questions.setQuestionName(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                questions.setQuestion1(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_1)));
                questions.setQuestion2(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_2)));
                questions.setQuestion3(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_3)));
                questions.setQueestionNumber(cursor.getInt(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_ANSWER)));
                questionsList.add(questions);


            }while (cursor.moveToNext());
        }

        cursor.close();
        return questionsList;


    }




}
