package com.hfad.imdblogin.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Farhad on 25-03-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="registration.db";
    public static final String TABLE_USER = "register";

    private static final String COL_1="ID";
    private static final String COL_2="FirsName";
    private static final String COL_3="LastName";
    private static final String COL_4="Password";
    private static final String COL_5="Email";
    private static final String COL_6="Phone";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT,"
            + COL_3 + " TEXT," + COL_4 + " TEXT," + COL_5 + " TEXT,"
            + COL_6 + " TEXT" + ")";


    SQLiteDatabase myBD;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static String getEmail() {
        return COL_5;
    }
    public static String getPassword() {
        return COL_4;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS " + TABLE_USER;

        db.execSQL(query);

    }


    public void insert(int id, String firstName, String lastName, String password, String email, String phone ){

        myBD = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1,id);
        values.put(COL_2,firstName);
        values.put(COL_3,lastName);
        values.put(COL_4,password);
        values.put(COL_5,email);
        values.put(COL_6,phone);

        myBD.insert(TABLE_USER,null, values );

    }

    public long update(int id, String firstName, String lastName, String password, String email, String phone ){

        ContentValues values = new ContentValues();
            values. put(COL_1, id);
            values .put(COL_2,firstName);
            values .put(COL_3,lastName);
            values .put(COL_4,password);
            values .put(COL_5,email);
            values .put(COL_6,phone);

            String where = COL_1 + "=" + id;

        return myBD.update(TABLE_USER,values, where,null );
    }

    public long delete(int id ){

        String where = COL_1 + "=" + id;

        return myBD.delete(TABLE_USER,where,null );
    }

    public Cursor getAllRecords(){

        String query = "SELECT * FROM " + TABLE_USER;
        return myBD.rawQuery(query, null);
    }
}
