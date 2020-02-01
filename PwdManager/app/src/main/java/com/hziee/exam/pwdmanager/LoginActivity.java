package com.hziee.exam.pwdmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private EditText mLoginPwd;         //主密码编辑框
    private Button mLoginConfirm;       //确认登录按钮
    private SQLiteDatabase db;

    private ArrayList<Password> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initDatabase();
        setListener();
    }

    //初始化控件
    private void initView() {
        mLoginPwd = findViewById(R.id.et_login_pwd);
        mLoginConfirm = findViewById(R.id.bt_login_confirm);
    }

    //初始化数据库
    private void initDatabase() {
        MyDbHelper helper = new MyDbHelper(this,"test.db",null,1);
        db = helper.getWritableDatabase();
    }

    //设置监听器
    private void setListener() {
        //检查主密码是否正确
        mLoginConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置默认主密码为123
                if ("123".equals(mLoginPwd.getText().toString())) {
                    queryData();
                    sendData();
                } else {
                    Toast.makeText(LoginActivity.this, "密码错误，请重新输入！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //数据库中查找所有数据
    private void queryData() {
        Cursor cursor = db.query("pwd",null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            Password password = new Password(title,pwd);
            mData.add(password);
        }
    }

    //将数据传递给PwdListActivity
    private void sendData() {
        Intent intent = new Intent(LoginActivity.this, PwdListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mData",mData);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
