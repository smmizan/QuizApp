package com.smmizan.quizapp;

import android.provider.BaseColumns;

public final class Contract {



    private Contract(){}



    public static class CagetoriesTable implements BaseColumns{

        public static final String TABLE_CATEGORIES = "quiz_categories";
        public static final String COLUMN_NAME = "categories_name";

    }


    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "questions";
        public static final String COLUMN_QUESTION_1 = "qestion1";
        public static final String COLUMN_QUESTION_2 = "qestion2";
        public static final String COLUMN_QUESTION_3 = "qestion3";
        public static final String COLUMN_QUESTION_ANSWER = "question_ans";
        public static final String COLUMN_QUESTION_DIFF_LEVEL = "question_level";
        public static final String COLUMN_CATEGORIES = "categories_id";
    }
}
