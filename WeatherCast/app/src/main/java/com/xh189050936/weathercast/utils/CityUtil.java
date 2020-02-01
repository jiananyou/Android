package com.xh189050936.weathercast.utils;

import android.net.Uri;
import android.util.Log;

import com.xh189050936.weathercast.bean.City;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityUtil {
    private static final String TAG = "CityUtil";
    private static final String CITY_URL
            = "http://api.k780.com/?app=weather.city&cou=1&appkey=47456&sign=09799d406041d95a5176cd35d6d9272f&format=json";

    /**
     * 获取所有城市
     */
    public List<City> getCityList() {
        List<City> items = new ArrayList<>();

        try {
            String url = Uri.parse(CITY_URL).buildUpon().build().toString();
            String jsonString = ApiUtil.getURLString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseCity(items, jsonBody);
            System.out.println(items);
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON", e);
        }
        return items;
    }

    private void parseCity(List<City> items, JSONObject jsonBody) throws JSONException {
        JSONObject data = jsonBody.getJSONObject("result").getJSONObject("datas");
        for (int i = 0,j = 0; j < 100; i++,j++) {
            JSONObject cityObject = null;
            try {
                cityObject = (JSONObject) data.get(String.valueOf(i+1));
            } catch (JSONException e) {
                j--;
                continue;
            }
            City city = new City();
            city.setId(cityObject.getString("cityid"));
            city.setName(cityObject.getString("citynm"));
            items.add(city);
        }
    }
}
