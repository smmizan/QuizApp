package com.smzn.quizapp;

import android.provider.BaseColumns;

public final class Contract {



    private Contract(){}

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "questions";
        public static final String COLUMN_QUESTION_1 = "qestion1";
        public static final String COLUMN_QUESTION_2 = "qestion2";
        public static final String COLUMN_QUESTION_3 = "qestion3";
        public static final String COLUMN_QUESTION_ANSWER = "question_ans";
    }
}
