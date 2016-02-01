package com.example.chanwon.appsent.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by CHANWON on 7/24/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sentence.db";
    public static final String TB_NAME = "sentence_table";
    public static final String TB_NAME1 = "emotion_table";
    public static final String TB_NAME2 = "sentiment_table";
    public static final String COL1 = "REVIEWID";
    public static final String COL2 = "SENTENCE";
    public static final String COL3 = "SDATE";
    public static final String COL4 = "SENTENCEID";
    public static final String COL5 = "EMOTION";
    public static final String COL6 = "SENTIMENT";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("CREATE TABLE " + TB_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, REVIEWID INTEGER, SENTENCE TEXT, SDATE DATE)");
//        db.execSQL("CREATE TABLE " + TB_NAME1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENTENCEID INTEGER, HAPPY TEXT, SAD TEXT, ANGER TEXT, FEAR TEXT, DISGUST TEXT, SURPRISE TEXT)");
//        db.execSQL("CREATE TABLE " + TB_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENTENCEID INTEGER, VPOSITIVE TEXT, POSITIVE TEXT, NEUTRAL TEXT, NEGATIVE TEXT, VNEGATIVE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);

    }

    public boolean insertData(String reviewID, String sentences, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, reviewID);
        contentValues.put(COL2, sentences);
        contentValues.put(COL3, date);
        long result = db.insert(TB_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertData1(String sentenceID, String happy, String sad, String anger, String fear, String disgust, String surprise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, sentenceID);
        contentValues.put("HAPPY", happy);
        contentValues.put("SAD", sad);
        contentValues.put("ANGER", anger);
        contentValues.put("FEAR", fear);
        contentValues.put("DISGUST", disgust);
        contentValues.put("SURPRISE", surprise);

        long result = db.insert(TB_NAME1, null, contentValues);
        return result != -1;
    }

    public boolean insertData2(String sentenceID, String vnegative, String negative, String neutral, String positive, String vpositive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, sentenceID);
        contentValues.put("VPOSITIVE", vpositive);
        contentValues.put("POSITIVE", positive);
        contentValues.put("NEUTRAL", neutral);
        contentValues.put("NEGATIVE", negative);
        contentValues.put("VNEGATIVE", vnegative);

        long result = db.insert(TB_NAME2, null, contentValues);
        return result != -1;
    }


    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TB_NAME, "ID = ?", new String[]{id});

    }

    public Cursor getTimeSentiment(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sdate, count(*) from " + TB_NAME + " where sentiment = '" + type + "'group by sdate", null);
        return res;
    }

    public Cursor getTimeEmotion(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sdate, count(*) from " + TB_NAME + " where emotion = '" + type + "'group by sdate", null);
        return res;
    }

    public Cursor countSentiment() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(vnegative), sum(negative), sum(neutral), sum(positive), sum(vpositive) from sentiment_table", null);
        return res;
    }

    public Cursor countStars() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(stars1), sum(stars2), sum(stars3), sum(stars4), sum(stars4) from stars_table", null);
        return res;
    }

    public Cursor countAllSentences() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select distinct count(reviewid) from sentence_table", null);
        return res;
    }

    public Cursor countAllSentencesStars() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count(*) from stars_table", null);
        return res;
    }


    public Cursor countEmotion() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(happy), sum(sad), sum(anger), sum(fear), sum(disgust), sum(surprise) from emotion_table", null);
        return res;
    }

    public Cursor countTimeSentiment() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select month, sum(vpositive), sum(positive), sum(neutral), sum(negative), sum(vnegative) from sentiment_table group by month", null);
        return res;
    }

    public Cursor countTimeEmotion() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select month, sum(happy), sum(sad), sum(anger), sum(fear), sum(disgust), sum(surprise) from emotion_table group by month", null);
        return res;
    }

    public Cursor countTimeStars() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select month, sum(stars1), sum(stars2), sum(stars3), sum(stars4), sum(stars5) from stars_table group by month", null);
        return res;
    }

    public Cursor rankPositiveFeature() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN sentiment_table\n" +
                "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                "Where VPOSITIVE+POSITIVE > 0.4\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor rankNegativeFeature() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN sentiment_table\n" +
                "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                "Where VNEGATIVE+NEGATIVE > 0.4 \n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor rankNeutralFeature() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN sentiment_table\n" +
                "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                "Where NEUTRAL > 0.4\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor rankNeutralFeaturebyMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN sentiment_table\n" +
                "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                "Where NEUTRAL > 0.4 and month = '" + month + "'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor rankNegativeFeaturebyMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN sentiment_table\n" +
                "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                "Where VNEGATIVE+NEGATIVE > 0.4 and month = '" + month + "'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor rankPositiveFeaturebyMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN sentiment_table\n" +
                "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                "Where VPOSITIVE+POSITIVE > 0.4 and month = '" + month + "'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor getMonthList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select month from stars_table group by month", null);
        return res;
    }


    public void insertTop10FeatureListPositive(ArrayList featurelist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS positivefeaturetable");
        db.execSQL("CREATE table positivefeaturetable (NOUN TEXT)");
        for (int i = 0; i < featurelist.size(); i++) {
            db.execSQL("insert into positivefeaturetable (NOUN) values ('" + featurelist.get(i) + "')");
        }
    }

    public Cursor getlistedList(String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + tablename, null);
        return res;
    }

    public Cursor getTimeLinePositiveRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM sentiment_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.vpositive+A.positive > 0.4\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }
}