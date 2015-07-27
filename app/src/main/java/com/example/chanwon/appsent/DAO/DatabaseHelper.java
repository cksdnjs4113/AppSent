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
    public static final String DB_NAME = "sentiment.db";
    public static final String TB_NAME = "sentiment_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "SENTENCE";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TB_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENTENCE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
        onCreate(db);

    }
    public boolean insertData(String sentences){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,sentences);
        long result = db.insert(TB_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }
        else{
            return true;

        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TB_NAME, null);
        return res;
    }
    public Cursor getSelectedData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TB_NAME + " where ID =" + id, null);
        return res;
    }

    public Integer deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TB_NAME, "ID = ?", new String[] {id});

    }
}