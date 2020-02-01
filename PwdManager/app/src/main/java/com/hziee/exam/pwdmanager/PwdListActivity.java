package com.hziee.exam.pwdmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class PwdListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter<RecycleViewAdapter.VH> mRecycleViewAdapter;
    private Button mAddItem;

    private SQLiteDatabase db;
    private ArrayList<Password> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_list);
        initView();
        initDatabase();
        setListener();
    }

    //初始化控件
    private void initView() {
        //初始化RecycleView
        mRecyclerView = findViewById(R.id.rv_list_pwd);
        //初始化RecycleView数据
        getData();
        mRecycleViewAdapter = new RecycleViewAdapter(mData,this);
        //线性布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //为每条记录设置简单分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //设置Adapter
        mRecyclerView.setAdapter(mRecycleViewAdapter);

        mAddItem = findViewById(R.id.bt_list_add);
    }

    //初始化数据库
    private void initDatabase() {
        MyDbHelper helper = new MyDbHelper(this,"test.db",null,1);
        db = helper.getWritableDatabase();
    }

    //获取RecycleView数据
    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        mData = (ArrayList<Password>) bundle.getSerializable("mData");
    }

    //设置监听器
    private void setListener() {
        //跳转至AddPwdActivity
        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwdListActivity.this,AddPwdActivity.class);
                startActivity(intent);
            }
        });
    }
}
