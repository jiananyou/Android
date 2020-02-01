package com.example.afinal189050936;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username,et_password;
    private Button bt_register;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initDatabase();
        setListener();
    }

    private void initView(){
        et_username = super.findViewById(R.id.et_username);
        et_password = super.findViewById(R.id.et_password);
        bt_register = super.findViewById(R.id.bt_register);
    }

    private void initDatabase(){
        MyDbHelper helper = new MyDbHelper(this,"demo.db",null,1);
        db = helper.getWritableDatabase();
    }

    private void setListener(){
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_username.getText())) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(et_password.getText())){
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_LONG).show();
                } else{
                    String newUser = et_username.getText().toString();
                    String newPwd = et_password.getText().toString();
                    Cursor cursor = db.query("user",null,null,null,
                            null,null,null,null);
                    int flag = 1;

                    while (cursor.moveToNext()){
                        String username = cursor.getString(cursor.getColumnIndex("username"));
                        if (newUser.equals(username)){
                            flag = 0;
                            break;
                        }
                    }
                    if (flag == 1){
                        ContentValues values = new ContentValues();
                        values.put("username",newUser);
                        values.put("password",newPwd);

                        db.insert("user",null,values);

                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),"用户名已存在！",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}
