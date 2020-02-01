package com.xh189050936.weathercast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xh189050936.weathercast.bean.City;
import com.xh189050936.weathercast.database.CityCursorWrapper;
import com.xh189050936.weathercast.database.CityDbSchema;
import com.xh189050936.weathercast.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 实例中只维护一个Lab对象
 * 用于快捷操作用户存储的城市信息
 */
public class CityLab {
    private static CityLab sCityLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //单例模式
    public static CityLab get(Context context) {
        if (sCityLab == null) {
            sCityLab = new CityLab(context);
        }
        return sCityLab;
    }

    //封闭的构造方法
    private CityLab(Context context) {
        //初始化
        mContext = context.getApplicationContext();
        mDatabase = new DBHelper(context)
                .getWritableDatabase();
    }

    /**
     * 包装指针
     *
     * @param whereClause 查询的字段
     * @param whereArgs   查询参数
     * @return 指针的包装对象
     */
    private CityCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CityDbSchema.CityTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new CityCursorWrapper(cursor);
    }

    /**
     * 包装内容，便于存储
     *
     * @param city 城市对象
     */
    private static ContentValues getContentValues(City city) {
        ContentValues values = new ContentValues();
        values.put(CityDbSchema.CityTable.Cols.ID, city.getId());
        values.put(CityDbSchema.CityTable.Cols.NAME, city.getName());
        return values;
    }

    /**
     * 获取储存的所有城市
     */
    public List<City> getCities() {
        List<City> cityList = new ArrayList<>();
        CityCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cityList.add(cursor.getCity());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cityList;
    }

    public void addCity(City city) {
        ContentValues values = getContentValues(city);
        mDatabase.insert(CityDbSchema.CityTable.NAME, null, values);
    }

    public void deleteCity(City city) {
        mDatabase.delete(CityDbSchema.CityTable.NAME, CityDbSchema.CityTable.Cols.NAME + "=?", new String[]{city.getName()});
    }

    public boolean isExist(City city) {
        CityCursorWrapper cursor = queryCrimes(CityDbSchema.CityTable.Cols.NAME + "=?", new String[]{city.getName()});
        return cursor.moveToFirst();
    }

}
