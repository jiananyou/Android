package com.example.afinal189050936;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private EditText et_username,et_password;
    private Button bt_login,bt_register;
    private SQLiteDatabase db;
    private ArrayList<HashMap<String,Object>> Income;
    private ArrayList<HashMap<String,Object>> Expend;

    private Float totalIncome = 0f;
    private Float totalExpend = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initDatabase();
        setListener();
    }

    //初始化文本框信息
    private void initView(){
        et_username = super.findViewById(R.id.et_username);
        et_password = super.findViewById(R.id.et_password);
        bt_login = super.findViewById(R.id.bt_login);
        bt_register = super.findViewById(R.id.bt_register);
    }

    //初始化数据库
    private void initDatabase(){
        MyDbHelper helper = new MyDbHelper(this,"demo.db",null,1);
        db = helper.getWritableDatabase();
    }

    //设置监听器
    private void setListener(){
        //登录事件
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_username.getText())) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(et_password.getText())){
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    String username = et_username.getText().toString();
                    String password = et_password.getText().toString();
                    Cursor cursor = db.query("user",null,null,null,
                            null,null,null,null);
                    Boolean checkLogin = false;

                    while (cursor.moveToNext()){
                        String existUser = cursor.getString(cursor.getColumnIndex("username"));
                        String existPwd = cursor.getString(cursor.getColumnIndex("password"));
                        if(username.equals(existUser) && password.equals(existPwd)){
                            checkLogin = true;
                        }
                    }
                    if (checkLogin == true){
                        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("username",username);
                        editor.commit();

                        Income = new ArrayList<>();
                        Expend = new ArrayList<>();

                        cursor = db.query("income",new String[]{"projectName","money","date","type","mark"},"username='"+username+"'",null,null,null,null);
                        while (cursor.moveToNext()){
                            String projectName = cursor.getString(cursor.getColumnIndex("projectName"));
                            Float money = cursor.getFloat(cursor.getColumnIndex("money"));
                            String date = cursor.getString(cursor.getColumnIndex("date"));
                            String type = cursor.getString(cursor.getColumnIndex("type"));
                            String mark = cursor.getString(cursor.getColumnIndex("mark"));
                            totalIncome += money;
                            HashMap<String,Object> item = new HashMap<>();
                            item.put("projectName",projectName);
                            item.put("money","+" + money);
                            item.put("date",date);
                            item.put("type",type);
                            item.put("mark",mark);
                            Income.add(item);
                        }

                        cursor = db.query("expend",new String[]{"projectName","money","date","type","mark"},"username='"+username+"'",null,null,null,null);
                        while (cursor.moveToNext()){
                            String projectName = cursor.getString(cursor.getColumnIndex("projectName"));
                            Float money = cursor.getFloat(cursor.getColumnIndex("money"));
                            String date = cursor.getString(cursor.getColumnIndex("date"));
                            String type = cursor.getString(cursor.getColumnIndex("type"));
                            String mark = cursor.getString(cursor.getColumnIndex("mark"));
                            totalExpend += money;
                            HashMap<String,Object> item = new HashMap<>();
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
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //注册事件
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
