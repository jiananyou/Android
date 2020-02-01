package com.xh189050936.weathercast;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xh189050936.weathercast.bean.City;
import com.xh189050936.weathercast.utils.CityUtil;

import java.util.List;

public class CityListFragment extends Fragment {
    public static final String TAG = "CityListFragment";

    private RecyclerView mCityRecyclerView;
    private CityAdapter mAdapter;

    private List<City> mCityList;

    private CallBacks mCallBacks;

    public interface CallBacks {
        void onClickCity(City city);
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

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new CityTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city, container, false);

        mCityRecyclerView = v.findViewById(R.id.city_recycle);
        mCityRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    private void updateUI() {
        mAdapter = new CityAdapter(mCityList);
        mCityRecyclerView.setAdapter(mAdapter);
    }

    private class CityTask extends AsyncTask<Void, Void, List<City>> {
        @Override
        protected List<City> doInBackground(Void... voids) {
            return new CityUtil().getCityList();
        }

        @Override
        protected void onPostExecute(List<City> cities) {
            mCityList = cities;
            updateUI();
        }
    }

    private class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private City mCity;
        private TextView mNameTextView;

        public CityHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mNameTextView = itemView.findViewById(R.id.tv_user_city_name);
        }

        public void bind(City city) {
            mCity = city;
            mNameTextView.setText(mCity.getName());
        }

        @Override
        public void onClick(View v) {
            mCallBacks.onClickCity(mCity);
        }
    }

    private class CityAdapter extends RecyclerView.Adapter<CityHolder> {

        private List<City> mCityList;

        public CityAdapter(List<City> cityList) {
            mCityList = cityList;
        }

        @NonNull
        @Override
        public CityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.list_item_user_city, viewGroup, false);
            return new CityHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CityHolder holder, int i) {
            City city = mCityList.get(i);
            holder.bind(city);
        }

        @Override
        public int getItemCount() {
            return mCityList.size();
        }

        public void setCityList(List<City> cityList) {
            mCityList = cityList;
        }
    }

}
