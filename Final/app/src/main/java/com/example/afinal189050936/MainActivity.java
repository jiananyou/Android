package com.example.afinal189050936;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments = new ArrayList<>(0);
    private ViewPager viewPager;
    private SQLiteDatabase db;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public void skipIncome(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,AddIncomeActivity.class);
        startActivity(intent);
    }

    public void skipExpend(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,AddExpendActivity.class);
        startActivity(intent);
    }

    public void logout(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(MainActivity.this,"用户已登出",Toast.LENGTH_LONG).show();
    }

    private void initDatabase(){
        MyDbHelper helper = new MyDbHelper(this,"demo.db",null,1);
        db = helper.getWritableDatabase();
    }

    private void initFragments(){
        FirstFragment fragment1 = new FirstFragment();
        SecondFragment fragment2 = new SecondFragment();
        ThirdFragment fragment3 = new ThirdFragment();
        FourthFragment fragment4 = new FourthFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_me:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initDatabase();

        viewPager = findViewById(R.id.viewPager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
