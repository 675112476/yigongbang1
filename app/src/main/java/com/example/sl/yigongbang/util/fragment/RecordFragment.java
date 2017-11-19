package com.example.sl.yigongbang.util.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.FruitAdapter;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends BaseFragment{
    LinearLayout finishedLinear;
    LinearLayout unfinishedLinear;
    private List<Activity>ActivityList=new ArrayList<Activity>();
    private FruitAdapter adapter;
    private RecyclerView recyclerView3;
    @Override
    protected void initView() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    private String [] record_finished={"1","2"};
    private String [] record_unfinished={"1","2"};
   // private ListView listView2;
    //private ListView listView1;

//    @Override
//    protected void getDataFromServer() {
//        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getCollected",new OkHttpClientManager.ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                Toast.makeText(mContext,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
//                Log.e("错误：",e.toString());
//            }
//
//            @Override
//            public void onResponse(String u) {
//                record_finished=splitString(u);
//                Log.e("----------Record回调内容",u);
//
//                for(int i=0;i<record_finished.length;i++)
//                {
//                    Log.e("----------Record结果",record_finished[i]);
//                }
//               // ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,record_finished);
//               // listView1.setAdapter(adapter3);
//            }
//        });
//        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getCollected",new OkHttpClientManager.ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                Log.e("错误：",e.toString());
//            }
//
//            @Override
//            public void onResponse(String u) {
//                record_unfinished=splitString(u);
//                Log.e("----------Record回调内容",u);
//
//                for(int i=0;i<record_unfinished.length;i++)
//                {
//                    Log.e("----------Record结果",record_unfinished[i]);
//                }
//                //ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,record_unfinished);
//                //listView2.setAdapter(adapter4);
//            }
//        });
//    }

//    public String[] splitString(String string){
//
//        string=string.substring(1,string.length()-1);
//        String[] array = string.split(",");
//
//        return array;
//    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar11);
        //listView1=(ListView)view.findViewById(R.id.List_view1);
        //listView2=(ListView)view.findViewById(R.id.List_view2);
        finishedLinear=(LinearLayout)getActivity().findViewById(R.id.finished_record);
        finishedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment1 fragment1 = new Fragment1();
                getFragmentManager().beginTransaction().replace(R.id.record_container, fragment1).commit();
            }
        });
        unfinishedLinear=(LinearLayout)getActivity().findViewById(R.id.unfinished_record);
        unfinishedLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment2 fragment2 = new Fragment2();
                getFragmentManager().beginTransaction().replace(R.id.record_container, fragment2).commit();
            }
        });

        getDataFromServer();
//        recyclerView3=(RecyclerView)view.findViewById(R.id.recyclerview_3);
//        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);//网格布局 有两列
//        recyclerView3.setLayoutManager(layoutManager);//网格布局
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }
protected void getDataFromServer() {
    OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getAll",
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
                        Log.e("----Record Act_name",attribute.getActName());
                    }
//                    adapter=new FruitAdapter(ActivityList);
//                    recyclerView3.setAdapter(adapter);//适配器实例与recyclerView控件关联
                }
            });
}

}
