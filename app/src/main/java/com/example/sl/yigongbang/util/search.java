package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Global_Data;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

public class search extends AppCompatActivity {


    private scut.carson_ho.searchview.SearchView searchView;
    private android.support.v7.widget.RecyclerView search_list;
    public void getDataFromServer(String string){
        OkHttpClientManager.getAsyn(Ip.getIp() + "Volunteer_ssh/activity_getActivity?actName=" + string, new OkHttpClientManager.ResultCallback<List<Activity>>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("----", "onError: "+"getivity:"+e.toString() );
                Toast.makeText(search.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<Activity> ActivityList) {
                FruitAdapter adapter=new FruitAdapter(ActivityList);
                search_list.setAdapter(adapter);//适配器实例与recyclerView控件关联
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_list=(android.support.v7.widget.RecyclerView)findViewById(R.id.search_list);
        searchView=(scut.carson_ho.searchview.SearchView)findViewById(R.id.search_view);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                getDataFromServer(string);
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
