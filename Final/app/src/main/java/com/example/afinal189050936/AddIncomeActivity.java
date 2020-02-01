package com.example.afinal189050936;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddIncomeActivity extends AppCompatActivity {
    private EditText et_projectName;
    private EditText et_money;
    private EditText et_date;
    private RadioGroup rg_type;
    private EditText et_mark;

    private Button bt_submit;
    private Button bt_cancel;

    String username;
    private String projectName;
    private Float money;
    private Float totalIncome = 0f;
    private Float totalExpend = 0f;
    private String date;
    private String mark;
    private String type = "信用卡";

    private SQLiteDatabase db;
    private ArrayList Income = new ArrayList<Map<String,Object>>();
    private ArrayList Expend = new ArrayList<Map<String,Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        initView();
        initDatabase();
        setListener();
    }

    private void setListener(){
        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                switch (i){
                    case R.id.rb_card:
                        type = "信用卡";
                        break;
                    case R.id.rb_cash:
                        type = "现金";
                        break;
                }
            }
        });

        et_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        et_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showDatePickDlg();
                }
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                username = sp.getString("username","");
                projectName = et_projectName.getText().toString();
                money = Float.parseFloat(et_money.getText().toString());
                date = et_date.getText().toString();
                mark = et_mark.getText().toString();

                ContentValues values = new ContentValues();
                values.put("username",username);
                values.put("projectName",projectName);
                values.put("money",money);
                values.put("date",date);
                values.put("type",type);
                values.put("mark",mark);

                db.insert("income",null,values);

                getData();
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_LONG).show();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                username = sp.getString("username","");
                getData();
            }
        });
    }

    private void getData(){
        Cursor cursor = db.query("income",new String[]{"projectName","money","date","type","mark"},"username="+username,null,null,null,null);
        while (cursor.moveToNext()){
            String projectName = cursor.getString(cursor.getColumnIndex("projectName"));
            Float money = cursor.getFloat(cursor.getColumnIndex("money"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String mark = cursor.getString(cursor.getColumnIndex("mark"));
            totalIncome += money;
            HashMap item = new HashMap<String,Object>();
            item.put("projectName",projectName);
            item.put("money","+" + money);
            item.put("date",date);
            item.put("type",type);
            item.put("mark",mark);
            Income.add(item);
        }

        cursor = db.query("expend",new String[]{"projectName","money","date","type","mark"},"username="+username,null,null,null,null);
        while (cursor.moveToNext()){
            String projectName = cursor.getString(cursor.getColumnIndex("projectName"));
            Float money = cursor.getFloat(cursor.getColumnIndex("money"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String mark = cursor.getString(cursor.getColumnIndex("mark"));
            totalExpend += money;
            HashMap item = new HashMap<String,Object>();
            item.put("projectName",projectName);
            item.put("money","-" + money);
            item.put("date",date);
            item.put("type",type);
            item.put("mark",mark);
            Expend.add(item);
        }

        Intent intent = new Intent();
        intent.putExtra("Income",Income);
        intent.putExtra("Expend",Expend);
        intent.putExtra("totalIncome","总收入：" + totalIncome);
        intent.putExtra("totalExpend","总支出：" + totalExpend);
        intent.setClass(AddIncomeActivity.this,MainActivity.class);
        startActivity(intent);
    }

    protected void showDatePickDlg(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddIncomeActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                AddIncomeActivity.this.et_date.setText(year + "/" + month + "/" + dayOfMonth);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void initView(){
        et_projectName = findViewById(R.id.et_projectName);
        et_money = findViewById(R.id.et_money);
        et_date = findViewById(R.id.et_date);
        et_mark = findViewById(R.id.et_mark);
        rg_type = findViewById(R.id.rg_type);
        bt_submit = findViewById(R.id.bt_submit);
        bt_cancel = findViewById(R.id.bt_cancel);
    }

    private void initDatabase(){
        MyDbHelper helper = new MyDbHelper(this,"demo.db",null,1);
        db = helper.getWritableDatabase();
    }
}
