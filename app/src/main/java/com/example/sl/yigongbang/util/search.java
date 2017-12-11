package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sl.yigongbang.R;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

public class search extends AppCompatActivity {


    private scut.carson_ho.searchview.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView=(scut.carson_ho.searchview.SearchView)findViewById(R.id.search_view);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
}
