package com.hziee.exam.pwdmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class VerifyPassActivity extends AppCompatActivity {
    private TextView mVerifyTitle;
    private TextView mVerifyPwd;
    private Button mVerifySave;

    private SQLiteDatabase db;

    private String title;
    private String pwd;
    private ArrayList<Password> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_pass);
        initView();
        initDatabase();
        setListener();
    }

    //初始化控件
    private void initView() {
        mVerifyTitle = findViewById(R.id.tv_verify_title);
        mVerifyPwd = findViewById(R.id.tv_verify_pwd);
        mVerifySave = findViewById(R.id.bt_verify_save);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        pwd = intent.getStringExtra("pwd");
        mVerifyTitle.setText("标题：" + title);
        mVerifyPwd.setText("密码：" + pwd);
    }

    //初始化数据库
    private void initDatabase() {
        MyDbHelper helper = new MyDbHelper(this,"test.db",null,1);
        db = helper.getWritableDatabase();
    }

    //设置监听器
    private void setListener() {
        //确认记录密码
        mVerifySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                queryData();
                sendData();
            }
        });
    }

    //将新的一条记录插入数据库
    private void addData() {
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("pwd",pwd);
        db.insert("pwd",null,values);
    }

    //将记录全部查找出来
    private void queryData() {
        Cursor cursor = db.query("pwd",null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            mData.add(new Password(title,pwd));
        }
    }

    //发送数据给PwdListActivity
    private void sendData() {
        Intent intent = new Intent(VerifyPassActivity.this, PwdListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mData",mData);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
