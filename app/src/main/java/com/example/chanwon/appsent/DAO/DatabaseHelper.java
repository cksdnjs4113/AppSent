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
        Cursor res = db.rawQuery("select sum(stars1), sum(stars2), sum(stars3), sum(stars4), sum(stars5) from stars_table", null);
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

    public Cursor countAllSentencesStarsbyMonth() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count(*) from stars_table where month = '201502' or month = '201503' or month = '201504' or month = '201505'", null);
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
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL\n" +
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
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL\n" +
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
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where NEUTRAL > 0.4 and stopword.nouns IS NULL\n" +
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
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where NEUTRAL > 0.4 and stopword.nouns IS NULL and month = '201502' or NEUTRAL > 0.4 and stopword.nouns IS NULL and month = '201503' or NEUTRAL > 0.4 and stopword.nouns IS NULL and month = '201504' or NEUTRAL > 0.4 and stopword.nouns IS NULL and month = '201505'\n" +
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
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and month = '201502' or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and month = '201503' or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and month = '201504' or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and month = '201505'\n" + "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor rankPositiveFeaturebyMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN sentiment_table\n" +
                "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and month = '201502' or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and month = '201503' or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and month = '201504' or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and month = '201505'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor getMonthList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select month from stars_table group by month", null);
        return res;
    }


    public void insertTop10FeatureListPositive(String tablename, ArrayList featurelist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        db.execSQL("CREATE table " + tablename + " (NOUN TEXT)");
        for (int i = 0; i < featurelist.size(); i++) {
            db.execSQL("insert into " + tablename + " (NOUN) values ('" + featurelist.get(i) + "')");
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

    public Cursor getTimeLineNeutralRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM sentiment_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.neutral > 0.4\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }

    public Cursor getTimeLineNegativeRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM sentiment_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.vnegative+A.negative > 0.4\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }

    //HAPPY
    public Cursor rankHappyFeatrue() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns, count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where HAPPY > 0.2 and stopword.nouns IS NULL\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid)DESC limit 10", null);
        return res;
    }

    public Cursor rankHappyFeatureByMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where HAPPY > 0.2 and stopword.nouns IS NULL and month = '201502' or HAPPY > 0.2 and stopword.nouns IS NULL and month = '201503' or HAPPY > 0.2 and stopword.nouns IS NULL and month = '201504' or HAPPY > 0.2 and stopword.nouns IS NULL and month = '201505'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor getTimeLineHappyRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM emotion_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.Happy > 0.2\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }

    //SAD
    public Cursor rankSadFeature() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns, count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where sad > 0.2 and stopword.nouns IS NULL\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid)DESC limit 10", null);
        return res;
    }

    public Cursor rankSadFeatureByMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where sad > 0.2 and stopword.nouns IS NULL and month = '201502' or sad > 0.2 and stopword.nouns IS NULL and month = '201503' or sad > 0.2 and stopword.nouns IS NULL and month = '201504' or sad > 0.2 and stopword.nouns IS NULL and month = '201505'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor getTimeLineSadRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM emotion_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.sad > 0.2\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }

    //Anger
    public Cursor rankAngerFeatrue() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns, count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where anger > 0.2 and stopword.nouns IS NULL\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid)DESC limit 10", null);
        return res;
    }

    public Cursor rankAngerFeatureByMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where anger > 0.2 and stopword.nouns IS NULL and month = '201502' or anger > 0.2 and stopword.nouns IS NULL and month = '201503' or anger > 0.2 and stopword.nouns IS NULL and month = '201504' or anger > 0.2 and stopword.nouns IS NULL and month = '201505'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor getTimeLineAngerRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM emotion_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.Anger > 0.2\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }

    //FEAR
    public Cursor rankFearFeatrue() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns, count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where fear > 0.2 and stopword.nouns IS NULL\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid)DESC limit 10", null);
        return res;
    }

    public Cursor rankFearFeatureByMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where fear > 0.2 and stopword.nouns IS NULL and month = '201502' or fear > 0.2 and stopword.nouns IS NULL and month = '201503' or fear > 0.2 and stopword.nouns IS NULL and month = '201504' or fear > 0.2 and stopword.nouns IS NULL and month = '201505'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor getTimeLineFearRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM emotion_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.fear > 0.2\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }

    //DISGUST
    public Cursor rankDisgustFeatrue() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns, count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where disgust > 0.2 and stopword.nouns IS NULL\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid)DESC limit 10", null);
        return res;
    }

    public Cursor rankDisgustFeatureByMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where disgust > 0.2 and stopword.nouns IS NULL and month = '201502' or disgust > 0.2 and stopword.nouns IS NULL and month = '201503' or disgust > 0.2 and stopword.nouns IS NULL and month = '201504' or disgust > 0.2 and stopword.nouns IS NULL and month = '201505'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor getTimeLineDisgustRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM emotion_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.disgust > 0.2\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }

    //Surprise

    public Cursor rankSurpriseFeatrue() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns, count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where surprise > 0.2 and stopword.nouns IS NULL\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid)DESC limit 10", null);
        return res;
    }

    public Cursor rankSurpriseFeatureByMonth(String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT nouns_table.nouns , count(nouns_table.sentenceid)\n" +
                "FROM nouns_table \n" +
                "INNER JOIN emotion_table\n" +
                "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                "left join stopword\n" +
                "on stopword.nouns = nouns_table.nouns\n" +
                "Where surprise > 0.2 and stopword.nouns IS NULL and month = '201502' or surprise > 0.2 and stopword.nouns IS NULL and month = '201503' or surprise > 0.2 and stopword.nouns IS NULL and month = '201504' or surprise > 0.2 and stopword.nouns IS NULL and month = '201505'\n" +
                "Group by nouns_table.nouns\n" +
                "order by count(nouns_table.sentenceid) DESC limit 10", null);
        return res;
    }

    public Cursor getTimeLineSurpriseRank(String noun) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT A.month, count(B.sentenceid)\n" +
                "FROM emotion_table A\n" +
                "LEFT JOIN (select * FROM nouns_table where nouns='" + noun + "'  ) B\n" +
                "ON A.sentenceID=B.sentenceid\n" +
                "where A.Surprise > 0.2\n" +
                "Group by A.month\n" +
                "order by A.month ASC", null);
        return res;
    }

    public Cursor countEmotionforMonth() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(happy), sum(sad), sum(anger), sum(fear), sum(disgust), sum(surprise) from emotion_table where month = '201502' or month = '201503' or month = '201504' or month = '201505'", null);
        return res;
    }

    public Cursor countStarsbyMonth() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(stars1), sum(stars2), sum(stars3), sum(stars4), sum(stars5) from stars_table where month = '201502' or month = '201503' or month = '201504' or month = '201505'", null);
        return res;
    }

    public Cursor countSentimentbyMonth() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(vnegative), sum(negative), sum(neutral), sum(positive), sum(vpositive) from sentiment_table where month = '201502' or month = '201503' or month = '201504' or month = '201505'", null);
        return res;
    }

    public Cursor countVerbsforCoPositive(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoPositive(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or VPOSITIVE+POSITIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }

    public Cursor countVerbsforCoNeutral(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoNeutral(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or NEUTRAL > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }


    public Cursor countVerbsforCoNegative(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoNegative(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN sentiment_table\n" +
                    "ON nouns_table.sentenceID=sentiment_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or VNEGATIVE+NEGATIVE > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }


    public Cursor countVerbsforCoHappy(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoHappy(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or Happy > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }


    public Cursor countVerbsforCoSad(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoSad(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or Sad > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }


    public Cursor countVerbsforCoAnger(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoAnger(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or Anger > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }


    public Cursor countVerbsforCoFear(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoFear(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or Fear > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }


    public Cursor countVerbsforCoDisgust(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoDisgust(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or Disgust > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }


    public Cursor countVerbsforCoSurprise(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        } else {
            res = db.rawQuery("SELECT verbs_table.VERBS, count(verbs_table.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join verbs_table\n" +
                    "on nouns_table.SENTENCEID = verbs_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table.nouns\n" +
                    "Where Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201502'\n" +
                    "Or Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201503'\n" +
                    "Or Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201504'\n" +
                    "Or Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and month = '201505'\n" +
                    "Group by verbs_table.VERBS\n" +
                    "order by count(verbs_table.sentenceid) DESC limit 10", null);
        }
        return res;
    }

    public Cursor countNounsforCoSurprise(String nouns, String timeframe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (timeframe.equals("Overall")) {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        } else {
            res = db.rawQuery("SELECT nouns_table_copy.nouns, count(nouns_table_copy.SENTENCEID)\n" +
                    "FROM nouns_table \n" +
                    "INNER JOIN emotion_table\n" +
                    "ON nouns_table.sentenceID=emotion_table.sentenceid\n" +
                    "inner join nouns_table_copy\n" +
                    "on nouns_table_copy.SENTENCEID = nouns_table.SENTENCEID\n" +
                    "left join stopword\n" +
                    "on stopword.nouns = nouns_table_copy.nouns\n" +
                    "Where Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201502'\n" +
                    "Or Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201503' \n" +
                    "Or Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS ==  '" + nouns + "' and nouns_table_copy.NOUNS !=  '" + nouns + "' and month = '201504' \n" +
                    "Or Surprise > 0.4 and stopword.nouns IS NULL and nouns_table.NOUNS == '" + nouns + "' and nouns_table_copy.NOUNS != '" + nouns + "' and month = '201505' \n" +
                    "Group by nouns_table_copy.NOUNS\n" +
                    "order by count(nouns_table_copy.sentenceid) DESC limit 10\n", null);
        }
        return res;
    }

}