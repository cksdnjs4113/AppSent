package com.example.chanwon.appsent.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CHANWON on 7/24/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sentence.db";
    public static final String TB_NAME = "sentence_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "SENTENCE";
    public static final String COL3 = "SENTIMENT";
    public static final String COL4 = "EMOTION";
    public static final String COL5 = "SDATE";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TB_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENTENCE TEXT, SENTIMENT TEXT, EMOTION TEXT, SDATE DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);

    }

    public boolean insertData(String sentences, String sentiment, String emotion, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, sentences);
        contentValues.put(COL3, sentiment);
        contentValues.put(COL4, emotion);
        contentValues.put(COL5, date);
        long result = db.insert(TB_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;

        }
    }


    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TB_NAME, "ID = ?", new String[]{id});

}

    public Cursor getTimeSentiment(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sdate, count(*) from " + TB_NAME + " where sentiment = '"+ type +"'group by sdate", null);
        return res;
    }
    public Cursor getTimeEmotion(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sdate, count(*) from " + TB_NAME + " where emotion = '"+ type +"'group by sdate", null);
        return res;
    }

    public Cursor countSentiment() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sentiment, count(sentiment) from " + TB_NAME + " group by sentiment", null);
        return res;
    }

    public Cursor countAllSentences(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("count(*) from " + TB_NAME, null);
        return res;
    }

    public Cursor countEmotion() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select emotion, count(emotion) from " + TB_NAME + " group by emotion", null);
        return res;
    }

    public Cursor countPosSentEmot() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select emotion, count(emotion) from " + TB_NAME + " where sentiment = 'positive' group by emotion", null);
        return res;
    }

    public Cursor countNegSentEmot() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select emotion, count(emotion) from " + TB_NAME + " where sentiment = 'negative' group by emotion", null);
        return res;
    }
}