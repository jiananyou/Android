package com.example.afinal189050936;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondFragment extends Fragment {
    private ArrayList Contacts = new ArrayList<Map<String,Object>>();
    private TextView tv_totalIncome;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            switch (v.getId()){
                case R.id.bt_addIncome:
                    activity.skipIncome();
                    break;
            }
        }
    };

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.bt_addIncome).setOnClickListener(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        View header = inflater.inflate(R.layout.contact_list_header,null);
        Intent intent = getActivity().getIntent();

        tv_totalIncome = view.findViewById(R.id.tv_totalIncome);
        tv_totalIncome.setText(intent.getStringExtra("totalIncome"));

        Contacts = (ArrayList<Map<String,Object>>) intent.getSerializableExtra("Income");
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), Contacts,
                R.layout.contact_list_item,
                new String[]{"projectName","money","date","type","mark"},
                new int[]{R.id.tv_projectName,R.id.tv_money,R.id.tv_date,R.id.tv_type,R.id.tv_mark});
        ListView lv_income = view.findViewById(R.id.lv_income);
        lv_income.addHeaderView(header);

        lv_income.setAdapter(adapter);
        return view;
    }

}
