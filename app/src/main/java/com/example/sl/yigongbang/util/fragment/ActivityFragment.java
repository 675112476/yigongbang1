package com.example.sl.yigongbang.util.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.FruitAdapter;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Ip;
import com.example.sl.yigongbang.util.search;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

public class ActivityFragment extends BaseFragment{
   private List<String> data=new ArrayList<String>();
   private SwipeRefreshLayout mSwipeRefresh;
    //一个可以下拉刷新的listView对象
    private ListView fav_listView;

   Context context;
    @Override
    protected void initView() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_activity;
    }

    @Override
    protected void getDataFromServer() {
        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getAll",
                new OkHttpClientManager.ResultCallback<List<Activity>>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        Log.e("--------getData",e.toString());
                        Toast.makeText(mContext,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(List<Activity> us)
                    {

                        for(Activity attribute : us) {
                            data.clear();
                            data.add(attribute.getActName());
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,data);
                        fav_listView.setAdapter(adapter);
                    }
                });
    }

    public String[] splitString(String string){

        string=string.substring(1,string.length()-1);
        String[] array = string.split(", ");
        return array;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getContext();
        Toolbar toolbar = (Toolbar)view. findViewById(R.id.toolbar1);//ToolBar实例化 逻辑化
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);//通过setSupportActionBar方法引用ToolBar实例

        fav_listView =(ListView) view.findViewById(R.id.activity_list);
        getDataFromServer();
        mSwipeRefresh=(SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh4);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
                mSwipeRefresh.setRefreshing(false);//设置刷新按钮停止转动
            }
        });
    }


}
