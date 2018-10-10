package com.wallet.track.dev.melvin9.trackme.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create  table
        db.execSQL(SqlData.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + SqlData.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
    public void insertData(String income,String bank,String address) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SqlData.COLUMN_INCOME, income);
        values.put(SqlData.COLUMN_BANK, bank);
        values.put(SqlData.COLUMN_TIMESTAMP,0);
        values.put(SqlData.COLUMN_ADDRESS, address);
        values.put(SqlData.COLUMN_BALANCE, income);
        // insert row
        db.insert(SqlData.TABLE_NAME, null, values);

        // close db connection
        db.close();
    }
    public SqlData getData() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SqlData.TABLE_NAME,
                new String[]{SqlData.COLUMN_ID, SqlData.COLUMN_INCOME,SqlData.COLUMN_EXPENSE,SqlData.COLUMN_BALANCE,SqlData.COLUMN_BANK,SqlData.COLUMN_ADDRESS,SqlData.COLUMN_FOOD,SqlData.COLUMN_BILLS,SqlData.COLUMN_TRANSPORTATION,SqlData.COLUMN_HOME,SqlData.COLUMN_ENTERTAINMENT,SqlData.COLUMN_SHOPPING,SqlData.COLUMN_CLOTH,SqlData.COLUMN_HEALTH,SqlData.COLUMN_GIFT,SqlData.COLUMN_EDUCATION,SqlData.COLUMN_OTHERS,SqlData.COLUMN_TIMESTAMP},
                SqlData.COLUMN_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        SqlData data = new SqlData(
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_INCOME)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_EXPENSE)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_BALANCE)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_FOOD)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_BILLS)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_TRANSPORTATION)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_HOME)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_ENTERTAINMENT)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_SHOPPING)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_CLOTH)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_HEALTH)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_GIFT)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_EDUCATION)),
                cursor.getInt(cursor.getColumnIndex(SqlData.COLUMN_OTHERS)),
                cursor.getString(cursor.getColumnIndex(SqlData.COLUMN_BANK)),
                cursor.getString(cursor.getColumnIndex(SqlData.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(SqlData.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();
        return data;
    }

    public void updateData(int item,Double prev_price,Double pres_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        switch (item){
            case 0: values.put(SqlData.COLUMN_FOOD, prev_price+pres_price);break;
            case 1: values.put(SqlData.COLUMN_BILLS, prev_price+pres_price);break;
            case 2: values.put(SqlData.COLUMN_TRANSPORTATION, prev_price+pres_price);break;
            case 3: values.put(SqlData.COLUMN_HOME, prev_price+pres_price);break;
            case 4: values.put(SqlData.COLUMN_ENTERTAINMENT, prev_price+pres_price);break;
            case 5: values.put(SqlData.COLUMN_SHOPPING, prev_price+pres_price);break;
            case 6: values.put(SqlData.COLUMN_CLOTH, prev_price+pres_price);break;
            case 7: values.put(SqlData.COLUMN_GIFT, prev_price+pres_price);break;
            case 8: values.put(SqlData.COLUMN_EDUCATION, prev_price+pres_price);break;
            case 9: values.put(SqlData.COLUMN_OTHERS, prev_price+pres_price);break;
            case 10:values.put(SqlData.COLUMN_EXPENSE, prev_price+pres_price);break;
            case 11:values.put(SqlData.COLUMN_BALANCE, prev_price+pres_price);break;
        }

        // updating row
        db.update(SqlData.TABLE_NAME, values, SqlData.COLUMN_ID + " = ?",
                new String[]{String.valueOf(1)});
    }
    public void updateincome(double income){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlData.COLUMN_INCOME,income);
        // updating row
        db.update(SqlData.TABLE_NAME, values, SqlData.COLUMN_ID + " = ?",
                new String[]{String.valueOf(1)});

    }
    public void updateBank(String bank,String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlData.COLUMN_BANK,bank);
        values.put(SqlData.COLUMN_ADDRESS,address);
        // updating row
        db.update(SqlData.TABLE_NAME, values, SqlData.COLUMN_ID + " = ?",
                new String[]{String.valueOf(1)});

    }
    public void setdate(String Time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlData.COLUMN_TIMESTAMP,Time);
        // updating row
        db.update(SqlData.TABLE_NAME, values, SqlData.COLUMN_ID + " = ?",
                new String[]{String.valueOf(1)});
    }

}