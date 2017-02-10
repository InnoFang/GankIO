package com.innofang.gankiodemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.innofang.gankiodemo.constant.DbSchema;
import com.innofang.gankiodemo.constant.DbSchema.GankTable;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 17:56
 * Description:
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DbSchema.DATABASE_NAME, null, DbSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + GankTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                GankTable.Cols.DESC + ", " +
                GankTable.Cols.TYPE + ", " +
                GankTable.Cols.WHO + ", " +
                GankTable.Cols.PUBLISH_AT + ", " +
                GankTable.Cols.URL + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
