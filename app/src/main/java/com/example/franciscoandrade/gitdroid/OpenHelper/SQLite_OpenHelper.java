package com.example.franciscoandrade.gitdroid.OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by franciscoandrade on 1/9/18.
 */

public class SQLite_OpenHelper extends SQLiteOpenHelper{

    //Todo: create new column for user to enter their name, therefore dataabse has its real name and its github nickname

//Receives name of DB
    public SQLite_OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table gituser(_ID integer primary key autoincrement, Name text, Repos text, Data text);";
        db.execSQL(query);
    }
    //Modify structure of DB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS gituser");
////        //we create the class after we drop the older version
//        onCreate(db);
    }

    //Method allow to open DB
    public void openDB(){
        this.getWritableDatabase();
    }

    //Method allow to close DB
    public void closeDB(){
        this.close();
    }

    //Method allow to insert registros in table users
    public void insertUserData(String name){

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM gituser"+ " WHERE Name = '" + name +"';", null);
        if (cursor.getCount() == 0) {
            ContentValues values= new ContentValues();
            values.put("Name", name);
           // values.put("Repos", repo);
            this.getWritableDatabase().insert("gituser", null, values);
        }
        //cursor.close();

    }

    public void insertUserRepos(String name, String repo){

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM gituser"+ " WHERE Name = '" + name +"';", null);
//        if (cursor.getCount() > 0) {
            ContentValues values= new ContentValues();
            values.put("Name", name);
            values.put("Repos", repo);
            this.getWritableDatabase().insert("gituser", null, values);
        //}
        cursor.close();
    }

    //Method allow to verify if user exist

    public Cursor verifyUsrPas(String user) throws SQLException{
        Cursor cursor = getReadableDatabase().rawQuery(
                    "SELECT * FROM gituser WHERE Name = '" + user +
                            "';", null);

        cursor.moveToFirst();

//        Log.d("CURSORREspo==", "verifyUsrPas: "+cursor.moveToFirst());
        return  cursor;

    }

    public Cursor showUser(String userName) throws SQLException{
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM gituser"+ " WHERE Name = '" + userName +
                        "';", null);

        cursor.moveToFirst();

        return cursor;
    }



//SELECT * FROM users WHERE Email='isco@mail.com'  AND Password='1234'
}
//"Email='"+user+"'  AND Password='"+pass+"' "
//"Email='"+user+"' AND Password='"+pass+"'"