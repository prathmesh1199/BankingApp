package com.example.bankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "my_database";

    // Table Names
    private static final String DB_TABLE = "table_register";

    // column names
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";

    private static final String CREATE_TABLE_REGISTER = "CREATE TABLE " + DB_TABLE + "("+
            KEY_FNAME + " TEXT," +
            KEY_LNAME + " TEXT," + KEY_EMAIL + "TEXT," + KEY_PASS + "TEXT);";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE table_register(fname VARCHAR , lname VARCHAR , email VARCHAR , pass VARCHAR);";
        String number_table_query = "CREATE TABLE number_table(email TEXT , balance INT);";
        Log.d("here", "onCreate: Table Created");
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(number_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long credit_or_debit(String value , int flag , Context context , long curr_total , String email) {
        // flag = 1 : Credit
        // flag = -1 : debit
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        if(value.length() > 8) return -1; // Invalid operation as length is too big.

        long val = Long.parseLong(value);
        Log.d("here", "credit_or_debit: val : " + val);

        if(flag == 1) {

            if(val < 0) return -1; // Invalid operation.
            val+=curr_total;

        } else {
            if(val > curr_total) return -1;
            val = curr_total - val;
        }

        ContentValues cv = new ContentValues();
        cv.put("email" , email);
        cv.put("balance" , val);
        sqLiteDatabase.update("number_table" , cv , "email = ?" , new String[] {email});
        return val;

    }

    public int insert(String fname , String lname , String email , String pass) {
        if(fname.isEmpty() || lname.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            return 0;
        }

        Log.d("here", "insert: 1");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fname" , fname);
        contentValues.put("lname" , lname);
        contentValues.put("email" , email);
        contentValues.put("pass" , pass);

        ContentValues cv = new ContentValues();
        cv.put("email" , email);
        cv.put("balance" , 0);

        Log.d("here", "insert: 2");
        sqLiteDatabase.insert("table_register" , null , contentValues);
        sqLiteDatabase.insert("number_table" , null , cv);

        Log.d("here", "insert: 3");
        sqLiteDatabase.close();
        return 1;
    }

    public int get_balance(String email) {

        Log.d("here", "get_balance: 1");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Log.d("here", "get_balance: 2");
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM number_table WHERE email = '" + email + "'", null);

        Log.d("here", "get_balance: 3");
        if(c.moveToFirst()) {
            Log.d("here", "get_balance: 4");
            int idx = c.getColumnIndex("balance");
            int balance = c.getInt(idx);
            Log.d("here", "get_balance: " + balance);

            return balance;
        }

        return 0;
    }
    public int check(String email , String password) {
        Log.d("here", "check: 1");

        String savedUname = null;
        String savedPword = null;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM table_register WHERE email = '" + email + "'", null);
         Log.d("here", "check: fdfdfd");

        if (c.moveToFirst()) {

            Log.d("here", "check: 11");
            int unameIndex = c.getColumnIndex("email");
            int pwordIndex = c.getColumnIndex("pass");

            Log.d("here", "check: " + unameIndex + "  " + pwordIndex);
            Log.d("here", "check: 12");

            Log.d("here", "check: " + c.getString(unameIndex) + "  " + c.getString(pwordIndex));

            savedUname = c.getString(unameIndex);
            savedPword = c.getString(pwordIndex);

            Log.d("here", "check: 13");

            if (savedUname.equals(email)) {
                Log.d("here", "check: 14");
                if (savedPword.equals(password)) {
                    Log.d("here", "check: 15");
                    return 1;
                } else {
                    Log.d("here", "check: 16");
                    return 0;
                }
            }


        }
        Log.d("here", "check: 17");
        return 0;
    }


}
