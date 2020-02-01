package com.xh189050936.weathercast.utils;

import android.net.Uri;
import android.util.Log;

import com.xh189050936.weathercast.bean.DailyWeather;
import com.xh189050936.weathercast.bean.NowWeather;
import com.xh189050936.weathercast.bean.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherUtil {
    private static final String TAG = "WeatherUtil";
    private static final String WEATHER_URL = "http://v1.alapi.cn/api/weather";
    public static final String NOW = "/now";
    public static final String FORECAST = "/forecast";

    public List<Weather> getWeather(String cityName, String type) {
        List<Weather> items = new ArrayList<>();
        try {
            String url = Uri.parse(WEATHER_URL + type)
                    .buildUpon()
                    .appendQueryParameter("location", cityName)
                    .build().toString();
            String jsonString = ApiUtil.getURLString(url);
            /*Log.i(TAG, "Received JSON: " + jsonString);*/
            JSONObject jsonBody = new JSONObject(jsonString);
            if (type.equals(NOW)) {
                parseNowWeather(items, jsonBody);
                Log.i(TAG, "Get Nowadays Success!!!");
            } else {
                parseDailyWeather(items, jsonBody);
                Log.i(TAG, "Get Forecast Success!!!");
            }
            System.out.println(items);
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON", e);
        }
        return items;
    }

    private void parseNowWeather(List<Weather> items, JSONObject jsonBody) throws JSONException {
        JSONObject responseData = jsonBody.getJSONObject("data");
        JSONObject now = responseData.getJSONObject("now");
        NowWeather weather = new NowWeather();
        weather.parseJSON(now);
        items.add(weather);
    }

    private void parseDailyWeather(List<Weather> items, JSONObject jsonBody) throws JSONException {
        JSONObject responseData = jsonBody.getJSONObject("data");
        JSONArray weathers = responseData.getJSONArray("daily_forecast");
        DailyWeather daily;
        for (int i = 0; i < weathers.length(); i++) {
            //获取其中一天的天气对象
            JSONObject onDay = weathers.getJSONObject(i);
            daily = new DailyWeather();
            daily.parseJSON(onDay);
            if (i == 0) {
                daily.setWeek("今   日");
            } else if (i == 1) {
                daily.setWeek("明   天");
            } else {
                daily.setWeek(DateUtil.getWeek(daily.getDate()));
            }
            items.add(daily);
        }
    }
}
