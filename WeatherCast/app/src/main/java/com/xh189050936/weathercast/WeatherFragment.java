package com.xh189050936.weathercast;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xh189050936.weathercast.bean.DailyWeather;
import com.xh189050936.weathercast.bean.NowWeather;
import com.xh189050936.weathercast.bean.Weather;
import com.xh189050936.weathercast.utils.DateUtil;
import com.xh189050936.weathercast.utils.WeatherUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";
    private static final String KEY_NOW = "now";
    private static final String KEY_DAILY = "daily";
    private static final String ARG_CITY_NAME = "cityName";

    //组件定义
    private TextView mCityTextView;
    private TextView mTmpTextView;
    private TextView mConditionTextView;
    private ImageView mConditionImageView;
    private RecyclerView mRecyclerView;
    private CallBacks mCallBacks;

    private WeatherAdapter mAdapter;

    //变量定义
    private NowWeather nowWeather;
    private List<Weather> dailyWeatherList;
    private String cityName;

    public interface CallBacks {
        String getCityName();
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);//添加菜单栏
        cityName = mCallBacks.getCityName();
        new WeatherTask().execute(cityName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        mCityTextView = v.findViewById(R.id.tv_city);
        mTmpTextView = v.findViewById(R.id.tv_tmp);
        mConditionTextView = v.findViewById(R.id.tv_condition);
        mConditionImageView = v.findViewById(R.id.iv_condition);

        mRecyclerView = v.findViewById(R.id.daily_recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBacks = (CallBacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    @Nullable
    @Override
    public Object getSharedElementReturnTransition() {
        return super.getSharedElementReturnTransition();
    }

    /**
     * 创建菜单栏
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_select_city, menu);
    }

    /**
     * 菜单栏监听事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_city:
                System.out.println("select");
                selectCity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectCity() {
        Intent intent = new Intent(getActivity(), CityActivity.class);
        startActivity(intent);
    }

    private void setupAdapter() {
        if (isAdded()) {
            //设置当前天气
            String tmp = nowWeather.getTmp() + getActivity().getString(R.string.tmp_suffix);
            String wind = nowWeather.getWind_dir() + " " + nowWeather.getWind_sc() + "级";
            String pcpn = nowWeather.getPcpn() + getActivity().getString(R.string.pcpn_suffix);
            mCityTextView.setText(cityName + "市");
            mTmpTextView.setText(tmp);
            mConditionTextView.setText(nowWeather.getCond_txt());
            int iconId = getContext().getResources()
                    .getIdentifier("w" + nowWeather.getCond_code(), "drawable", getContext().getPackageName());
            mConditionImageView.setImageResource(iconId);
            //设置未来天气
            mRecyclerView.setAdapter(new WeatherAdapter(dailyWeatherList));
        }
    }

    private class WeatherTask extends AsyncTask<String, Void, Map<String, List<Weather>>> {
        @Override
        protected Map<String, List<Weather>> doInBackground(String... params) {
            HashMap<String, List<Weather>> map = new HashMap<>();
            //连接并获取数据
            WeatherUtil utils = new WeatherUtil();
            map.put(KEY_NOW, utils.getWeather(params[0], WeatherUtil.NOW));
            map.put(KEY_DAILY, utils.getWeather(params[0], WeatherUtil.FORECAST));
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, List<Weather>> weatherMap) {
            List<Weather> nowList = weatherMap.get(KEY_NOW);
            List<Weather> dailyList = weatherMap.get(KEY_DAILY);
            if (!nowList.isEmpty()) {
                nowWeather = (NowWeather) nowList.get(0);
            }
            if (!dailyList.isEmpty()) {
                dailyWeatherList = dailyList;
            }
            setupAdapter();
        }
    }

    private class WeatherHolder extends RecyclerView.ViewHolder {
        private DailyWeather mWeather;

        private TextView mDateTextView;
        private TextView mWeekTextView;
        private ImageView mConditionImage;
        private TextView mTmpTextView;

        public WeatherHolder(@NonNull View itemView) {
            super(itemView);
            mDateTextView = itemView.findViewById(R.id.re_tv_date);
            mWeekTextView = itemView.findViewById(R.id.re_tv_week);
            mConditionImage = itemView.findViewById(R.id.re_iv_cond);
            mTmpTextView = itemView.findViewById(R.id.re_tv_tmp);
        }

        public void bind(Weather weather) {
            mWeather = (DailyWeather) weather;
            mDateTextView.setText(mWeather.getDate().substring(5, 10));
            mWeekTextView.setText(mWeather.getWeek());
            String tmp = mWeather.getTmp_min() +
                    getActivity().getString(R.string.tmp_suffix) + " ~ " +
                    mWeather.getTmp_max() + getActivity().getString(R.string.tmp_suffix);
            mTmpTextView.setText(tmp);
            int iconId = getContext().getResources().getIdentifier("w" + mWeather.getCond_code_d(), "drawable", getContext().getPackageName());
            mConditionImage.setImageResource(iconId);
        }
    }

    private class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {

        private List<Weather> mWeatherList;

        public WeatherAdapter(List<Weather> weatherList) {
            mWeatherList = weatherList;
        }

        @NonNull
        @Override
        public WeatherHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.list_item_daily_weather, viewGroup, false);
            return new WeatherHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WeatherHolder weatherHolder, int i) {
            Weather weather = mWeatherList.get(i);
            weatherHolder.bind(weather);
        }

        @Override
        public int getItemCount() {
            return mWeatherList.size();
        }

        public void setDailyWeather(List<DailyWeather> dailyWeathers) {
            mWeatherList = dailyWeatherList;
        }
    }
}
