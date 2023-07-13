package com.example.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper{
    private SQLiteDatabase myDataBase;
    public static final String DATABASE_NAME = "PR2.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_History = "History";
    public static final String TABLE_Dictionary = "Dictionary";

    public static final String COLLUM_NAME_words = "words";
    public static final String COLLUM_NAME_meanings = "meanings";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_history_table = "CREATE TABLE " + TABLE_History + " (" +
  //              _ID + " INTEGER PRIMARY COLLUM_NAME," +
                COLLUM_NAME_words + " TEXT)";

        String create_dictionary_table = "CREATE TABLE Dictionary (words TEXT , meanings TEXT)";

        db.execSQL(create_dictionary_table);
        db.execSQL(create_history_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_history_table = "DROP TABLE IF EXISTS " + TABLE_History;
        String drop_dictionary_table = "DROP TABLE IF EXISTS " + TABLE_Dictionary;

        db.execSQL(drop_history_table);
        db.execSQL(drop_dictionary_table);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean addOneHistory(HistoryModel dictionary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLLUM_NAME_words, HistoryModel.getWords());


        long insert = db.insert(TABLE_History, null, cv);
        if(insert == -1){
            db.close();
            return  false;
        }
        else {
            db.close();
            return true;
        }
    }

    public boolean addOneDictionary(DictionaryModel dictionary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLLUM_NAME_words, DictionaryModel.getWords());
        cv.put(COLLUM_NAME_meanings, DictionaryModel.getMeanings());

        long insert = db.insert(TABLE_Dictionary, null, cv);
        if(insert == -1){
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }

//    public void deleteExistHistory(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString = "DELETE FROM "+ TABLE_History + " WHERE words NOT IN (select words from "+TABLE_History+")";
//        db.execSQL(queryString);
//        db.close();
//    }

    public void deleteALLHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ TABLE_History;
        db.execSQL(queryString);
        db.close();
    }

    public ArrayList<String> getHistory(){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT DISTINCT * FROM "+ TABLE_History;
        Cursor cursor = db.rawQuery(queryString, null);

        ArrayList<String> returnList = new ArrayList<String>();

        if(cursor.moveToFirst()){
            do {
                String words = cursor.getString(0);
                returnList.add(words);
//                DictionaryModel newDictionary = new DictionaryModel(words);

            }while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return returnList;
    }
    public ArrayList<String> getDictionary(){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM "+ TABLE_Dictionary;
        Cursor cursor = db.rawQuery(queryString, null);

        ArrayList<String> returnList = new ArrayList<String>();

        if(cursor.moveToFirst()){
            do {
                String words = cursor.getString(0);
                returnList.add(words);
//                DictionaryModel newDictionary = new DictionaryModel(words);

            }while (cursor.moveToNext());
//38:19
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<String> getSuggestion(String text){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT words FROM Dictionary WHERE words LIKE '"+text+ "%'" ;
        Cursor cursor = db.rawQuery(queryString, null);

        ArrayList<String> returnList = new ArrayList<String>();

        if(cursor.moveToFirst()){
            do {
                String words = cursor.getString(0);
                returnList.add(words);
//                DictionaryModel newDictionary = new DictionaryModel(words);

            }while (cursor.moveToNext());
//38:19
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public String getMeanings(String text){
        String returnMeanings ="0";
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT meanings FROM Dictionary WHERE words LIKE '"+text+ "%'" ;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            returnMeanings = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return returnMeanings;
    }

//    public Cursor getSuggestion(String text)
//    {
//        Cursor c = myDataBase.rawQuery("SELECT * FROM Dictionary WHERE words LIKE '"+ text+ "%'", null);
//        return c;
//
//    }
}