package com.math.cky.matrixcalculator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MyDatebase extends SQLiteOpenHelper {

    public MyDatebase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "matrix", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table matrix_history(_id int auto_increment primary key," +
                "type varchar(20) not null," +
                "time long not null,"+
                "arg1 varchar(256) not null," +
                "arg2 varchar(256) not null," +
                "result varchar(256) not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS matrix_history" );
        onCreate(db);
    }
}
