package com.smmizan.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.smmizan.quizapp.Contract.*;
import com.smmizan.quizapp.model.Categories;
import com.smmizan.quizapp.model.Questions;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="super_quiz.db";
    private static final int DATABASE_VERSION = 3;

    //Context context;


    private static MyDatabaseHelper instance;


    private SQLiteDatabase db;

    private MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MyDatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new MyDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;

        final String CREATE_QUIZ_CATEGORIES = "CREATE TABLE "+
                CagetoriesTable.TABLE_CATEGORIES + " ( "+
                CagetoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                CagetoriesTable.COLUMN_NAME + " TEXT " +
                " ) ";


        final String CEATE_QUIZ_QUESTIONS_TABLE = "CREATE TABLE "+
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION_1 + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION_2 + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION_3 + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION_ANSWER + " INTEGER, " +
                QuestionsTable.COLUMN_QUESTION_DIFF_LEVEL + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORIES + " INTEGER, "+
                "FOREIGN KEY("+QuestionsTable.COLUMN_CATEGORIES+") REFERENCES "+
                CagetoriesTable.TABLE_CATEGORIES+ "(" + CagetoriesTable._ID + ")" + "ON DELETE CASCADE"+
                " ) ";

        db.execSQL(CREATE_QUIZ_CATEGORIES);

        db.execSQL(CEATE_QUIZ_QUESTIONS_TABLE);

        quizQuestionsCategories();
        quizQuestionsBank();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ CagetoriesTable.COLUMN_NAME);
        db.execSQL("DROP TABLE "+ QuestionsTable.TABLE_NAME);
        onCreate(db);

        Log.e("mizan","on ugrade");

    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }



    private void quizQuestionsCategories(){
        Categories categories_1 = new Categories("Programming");
        addCategories(categories_1);

        Categories categories_2 = new Categories("Data Structure");
        addCategories(categories_2);

        Categories categories_3 = new Categories("Physics");
        addCategories(categories_3);

        Categories categories_4 = new Categories("Graph Theory");
        addCategories(categories_4);


    }



    private void addCategories(Categories categories){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CagetoriesTable.COLUMN_NAME,categories.getName());
        db.insert(CagetoriesTable.TABLE_CATEGORIES,null,contentValues);

    }

    private void quizQuestionsBank(){
        Questions questions1 = new Questions("Programming : Lavel Easy : A is correct","A","B","C",1,Questions.DIFFICULTY_EASY,Categories.PROGRAMMING);
        addQuestions(questions1);

        Questions questions2 = new Questions("Programming : Lavel Hard : A is correct","A","B","C",1,Questions.DIFFICULTY_HARD,Categories.PROGRAMMING);
        addQuestions(questions2);

        Questions questions3 = new Questions("Data Structure : Lavel Medium : A is correct","A","B","C",1,Questions.DIFFICULTY_MEDIUM,Categories.DATA_STRUCTURE);
        addQuestions(questions3);

        Questions questions4 = new Questions("Graph Theory : Lavel Medium : A is correct","A","B","C",1,Questions.DIFFICULTY_MEDIUM,Categories.GRAPH_THEORY);
        addQuestions(questions4);

        Questions questions5 = new Questions("Physics : Lavel Easy : A is correct","A","B","C",1,Questions.DIFFICULTY_EASY,Categories.PHYSICS);
        addQuestions(questions5);

        Questions questions6 = new Questions("Physics : Lavel Easy : A is correct","A","B","C",1,Questions.DIFFICULTY_EASY,Categories.PHYSICS);
        addQuestions(questions6);
    }


    private void addQuestions(Questions questions){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionsTable.COLUMN_QUESTION,questions.getQuestionName());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_1,questions.getQuestion1());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_2,questions.getQuestion2());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_3,questions.getQuestion3());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_ANSWER,questions.getQueestionNumber());
        contentValues.put(QuestionsTable.COLUMN_QUESTION_DIFF_LEVEL,questions.getDifficultLavel());
        contentValues.put(QuestionsTable.COLUMN_CATEGORIES,questions.getCategoriesID());
        db.insert(QuestionsTable.TABLE_NAME,null,contentValues);
    }


    public List<Categories> getAllCategories(){
        List<Categories> categoriesList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM "+ CagetoriesTable.TABLE_CATEGORIES,null);
        if(cursor.moveToFirst()){
            do {
                Categories categories = new Categories();
                categories.setId(cursor.getInt(cursor.getColumnIndex(CagetoriesTable._ID)));
                categories.setName(cursor.getString(cursor.getColumnIndex(CagetoriesTable.COLUMN_NAME)));
                categoriesList.add(categories);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return categoriesList;
    }



    public ArrayList<Questions> getAllQuestions(){
        ArrayList<Questions> questionsList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME,null);

        if (cursor.moveToFirst()){
            do {

                Questions questions = new Questions();
                questions.setId(cursor.getInt(cursor.getColumnIndex(QuestionsTable._ID)));
                questions.setQuestionName(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                questions.setQuestion1(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_1)));
                questions.setQuestion2(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_2)));
                questions.setQuestion3(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_3)));
                questions.setQueestionNumber(cursor.getInt(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_ANSWER)));
                questions.setDifficultLavel(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_DIFF_LEVEL)));
                questions.setCategoriesID(cursor.getInt(cursor.getColumnIndex(QuestionsTable.COLUMN_CATEGORIES)));
                questionsList.add(questions);


            }while (cursor.moveToNext());
        }

        cursor.close();
        return questionsList;


    }



    public ArrayList<Questions> getQuestions(int categorieID,String difficultLavel){
        ArrayList<Questions> questionsList = new ArrayList<>();
        db = getReadableDatabase();

//        String selectionArray[] = new String[]{difficultLavel};
//
//
//        Cursor cursor = db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME + " WHERE "+
//                QuestionsTable.COLUMN_QUESTION_DIFF_LEVEL + " = ?",selectionArray);


        String selection = QuestionsTable.COLUMN_CATEGORIES + " = ? "+
                " AND " + QuestionsTable.COLUMN_QUESTION_DIFF_LEVEL + " = ? ";
        String selectionArgs[] = new String[]{String.valueOf(categorieID),difficultLavel};

        Cursor cursor = db.query(QuestionsTable.TABLE_NAME,null,selection,selectionArgs,null,null,null);




        if (cursor.moveToFirst()){
            do {

                Questions questions = new Questions();
                questions.setId(cursor.getInt(cursor.getColumnIndex(QuestionsTable._ID)));
                questions.setQuestionName(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                questions.setQuestion1(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_1)));
                questions.setQuestion2(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_2)));
                questions.setQuestion3(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_3)));
                questions.setQueestionNumber(cursor.getInt(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_ANSWER)));
                questions.setDifficultLavel(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION_DIFF_LEVEL)));
                questions.setCategoriesID(cursor.getInt(cursor.getColumnIndex(QuestionsTable.COLUMN_CATEGORIES)));
                questionsList.add(questions);


            }while (cursor.moveToNext());
        }

        cursor.close();
        return questionsList;


    }




}
