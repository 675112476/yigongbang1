package com.example.sl.yigongbang.util.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.util.List;

public class RecordFragment extends BaseFragment{

    @Override
    protected void initView() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    private String [] record_finished={"1","2"};
    private String [] record_unfinished={"1","2"};
    private ListView listView2;

    @Override
    protected void getDataFromServer() {
        Toast.makeText(mContext, "RecordFragment页面请求数据了", Toast.LENGTH_SHORT).show();

        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getCollected",new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("错误：",e.toString());
            }

            @Override
            public void onResponse(String u) {
                record_unfinished=splitString(u);
                Log.e("----------Record回调内容",u);

                for(int i=0;i<record_unfinished.length;i++)
                {
                    Log.e("----------Record结果",record_unfinished[i]);
                }
                ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,record_unfinished);
                listView2.setAdapter(adapter2);
            }
        });
        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getCollected",new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("错误：",e.toString());
            }

            @Override
            public void onResponse(String u) {
                record_finished=splitString(u);
                Log.e("----------Record回调内容",u);

                for(int i=0;i<record_finished.length;i++)
                {
                    Log.e("----------Record结果",record_finished[i]);
                }
                ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_2,record_finished);
                listView2.setAdapter(adapter3);
            }
        });


    }

    public String[] splitString(String string){

        string=string.substring(1,string.length()-1);
        String[] array = string.split(",");

        return array;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar11);
        ListView listView=(ListView)view.findViewById(R.id.List_view1);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,record_finished);
        //listView.setAdapter(adapter);
        getDataFromServer();
        listView2=(ListView)view.findViewById(R.id.List_view2);

    }
}
