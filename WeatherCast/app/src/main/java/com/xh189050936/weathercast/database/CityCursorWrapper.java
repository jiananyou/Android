package com.xh189050936.weathercast.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.xh189050936.weathercast.bean.City;

public class CityCursorWrapper extends CursorWrapper {
    public CityCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public City getCity() {
        //从表中获取字段信息
        String id = getString(getColumnIndex(CityDbSchema.CityTable.Cols.ID));
        String name = getString(getColumnIndex(CityDbSchema.CityTable.Cols.NAME));
        //赋值
        City city = new City();
        city.setId(id);
        city.setName(name);
        return city;
    }
}
