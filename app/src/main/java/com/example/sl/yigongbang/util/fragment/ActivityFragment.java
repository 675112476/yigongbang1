package com.example.sl.yigongbang.util.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.FruitAdapter;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.util.List;

public class ActivityFragment extends BaseFragment{
   private SwipeRefreshLayout mSwipeRefresh;
    //一个可以下拉刷新的listView对象
    private RecyclerView act_view;

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
                        FruitAdapter adapter=new FruitAdapter(us);
                        act_view.setAdapter(adapter);
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

        act_view =(RecyclerView) view.findViewById(R.id.act_view);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);//网格布局 有两列
        act_view.setLayoutManager(layoutManager);//网格布局
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
