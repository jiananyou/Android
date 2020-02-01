package com.example.afinal189050936;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {
    private EditText et_year;
    private EditText et_month;
    private EditText et_day;
    private TextView tv_sum;
    private Button bt_search;
    private RadioGroup rg_type;
    private String table = "income";
    private ListView lv_search;
    private Float sum = 0f;

    private String username,year,month,day,searchDate;

    private SQLiteDatabase db;
    private ArrayList search = new ArrayList<HashMap<String,Object>>();

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_first, container, false);
        final View header = inflater.inflate(R.layout.contact_list_header,container,false);
        lv_search = view.findViewById(R.id.lv_search);
        lv_search.addHeaderView(header);

        et_year = view.findViewById(R.id.et_year);
        et_month = view.findViewById(R.id.et_month);
        et_day = view.findViewById(R.id.et_day);
        bt_search = view.findViewById(R.id.bt_search);
        rg_type = view.findViewById(R.id.rg_type);
        tv_sum = view.findViewById(R.id.tv_sum);

        SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        username = sp.getString("username","");

        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                switch (i){
                    case R.id.rb_income:
                        table = "income";
                        break;
                    case R.id.rb_expend:
                        table = "expend";
                        break;
                }
            }
        });

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = et_year.getText().toString();
                month = et_month.getText().toString();
                day = et_day.getText().toString();
                if (month.isEmpty()){
                    searchDate = year;
                } else if (day.isEmpty()){
                    searchDate = year + "/" + month;
                } else {
                    searchDate = year + "/" + month + "/" + day;
                }
                search.clear();
                sum = 0f;
                initDatabase();
                Cursor cursor = db.query(table,new String[]{"projectName","money","date","type","mark"},"username='"+username+"'",null,null,null,null);
                while (cursor.moveToNext()){
                    String projectName = cursor.getString(cursor.getColumnIndex("projectName"));
                    Float money = cursor.getFloat(cursor.getColumnIndex("money"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    String mark = cursor.getString(cursor.getColumnIndex("mark"));
                    if (date.contains(searchDate)){
                        HashMap item = new HashMap<String,Object>();
                        item.put("projectName",projectName);
                        item.put("money",money);
                        sum += money;
                        item.put("date",date);
                        item.put("type",type);
                        item.put("mark",mark);
                        search.add(item);
                    }
                    if (table == "income"){
                        tv_sum.setText("总收入：" + sum);
                    }else {
                        tv_sum.setText("总支出：" + sum);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(view.getContext(), search,
                            R.layout.contact_list_item,
                            new String[]{"projectName","money","date","type","mark"},
                            new int[]{R.id.tv_projectName,R.id.tv_money,R.id.tv_date,R.id.tv_type,R.id.tv_mark});
                    lv_search.setAdapter(adapter);
                }
            }
        });

        return view;
    }

    private void initDatabase(){
        MyDbHelper helper = new MyDbHelper(getContext(),"demo.db",null,1);
        db = helper.getWritableDatabase();
    }
}
