package com.hziee.exam.pwdmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class AddPwdActivity extends AppCompatActivity {
    private EditText mAddTitle;
    private EditText mAddPwd;
    private Button mAddConfirm;
    private EditText mGenerateNum;
    private CheckBox mUpperCheck;
    private CheckBox mAmazingCheck;
    private CheckBox mAtCheck;
    private Button mGeneratePwd;

    private boolean upperChecked = false;
    private boolean amazingChecked = false;
    private boolean atChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pwd);
        initView();
        setListener();
    }

    //初始化控件
    private void initView() {
        mAddTitle = findViewById(R.id.et_add_title);
        mAddPwd = findViewById(R.id.et_add_pwd);
        mAddConfirm = findViewById(R.id.bt_add_confirm);
        mGenerateNum = findViewById(R.id.et_generate_num);
        mUpperCheck = findViewById(R.id.cb_uppercase);
        mAmazingCheck = findViewById(R.id.cb_amazing);
        mAtCheck = findViewById(R.id.cb_at);
        mGeneratePwd = findViewById(R.id.bt_generate);
    }

    //设置监听器
    private void setListener() {
        //确认添加密码
        mAddConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mAddTitle.getText().toString();
                String pwd = mAddPwd.getText().toString();
                if (pwd.length() < 8) {
                    Toast.makeText(AddPwdActivity.this,"验证不通过，密码少于8位",Toast.LENGTH_SHORT).show();
                } else if (pwd.length() > 20) {
                    Toast.makeText(AddPwdActivity.this,"验证不通过，密码多于20位",Toast.LENGTH_SHORT).show();
                } else if (!pwd.matches("^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9@!]{8,20})$")) {
                    Toast.makeText(AddPwdActivity.this,"验证不通过，密码必须包含字母和数字",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddPwdActivity.this,VerifyPassActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("pwd",pwd);
                    startActivity(intent);
                }
            }
        });

        //勾选大写字母
        mUpperCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                upperChecked = !upperChecked;
            }
        });

        //勾选!符号
        mAmazingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                amazingChecked = !amazingChecked;
            }
        });

        //勾选@符号
        mAtCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                atChecked = !atChecked;
            }
        });

        //生成密码
        mGeneratePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                StringBuilder generatedPwd = new StringBuilder();
                sb.append(AddPwdActivity.this.getString(R.string.default_checked));
                if (upperChecked)
                    sb.append(AddPwdActivity.this.getString(R.string.uppercase_checked));
                if (amazingChecked)
                    sb.append(AddPwdActivity.this.getString(R.string.amazing_checked));
                if (atChecked)
                    sb.append(AddPwdActivity.this.getString(R.string.at_checked));
                Random random = new Random();
                int num = Integer.parseInt(mGenerateNum.getText().toString());
                for (int i = 1; i <= num; i++) {
                    generatedPwd.append(sb.charAt(random.nextInt(sb.length())));
                }
                mAddPwd.setText(generatedPwd.toString());
            }
        });
    }
}
