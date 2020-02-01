package com.xh189050936.weathercast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库，储存城市
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "city.db";

    private static final String CREATE_CITY = "create table " + CityDbSchema.CityTable.NAME +"("+
                                                CityDbSchema.CityTable.Cols.ID + " primary key, "+
                                                CityDbSchema.CityTable.Cols.NAME+ ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists city");
        db.execSQL(CREATE_CITY);
    }
}
