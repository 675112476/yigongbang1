package com.example.sl.yigongbang.util.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.FruitAdapter;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends BaseFragment {
    private RecyclerView recyclerView2;
    private List<Activity> ActivityList=new ArrayList<Activity>();
    private FruitAdapter adapter;

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromServer();
        recyclerView2=(RecyclerView)getActivity().findViewById(R.id.recyclerview_2);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);//网格布局 有两列
        recyclerView2.setLayoutManager(layoutManager);//网格布局
    }

    @Override
    protected void initView() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fragment2;
    }
    protected void getDataFromServer() {
        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getUnFinished",
                new OkHttpClientManager.ResultCallback<List<Activity>>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        Log.e("--------getData",e.toString());
                        Toast.makeText(getContext(),"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(List<Activity> us)
                    {
                        ActivityList=us;
                        for(Activity attribute : ActivityList) {
                            Log.e("-----fragment2 Act_name",attribute.getActName());
                        }
                        adapter=new FruitAdapter(ActivityList);
                        recyclerView2.setAdapter(adapter);//适配器实例与recyclerView控件关联
                    }
                });
    }
}

