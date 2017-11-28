package com.example.sl.yigongbang.util.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Detail;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends BaseFragment{
   private List<Activity> data;
   private ListView listview;
   private SwipeRefreshLayout mSwipeRefresh;
    @Override
    protected void initView() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_favourite;
    }

    @Override
    protected void getDataFromServer() {
        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getCollected",new OkHttpClientManager.ResultCallback<List<Activity>>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("--------Favourite错误",e.toString());
                Toast.makeText(mContext,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<Activity> u) {
                data = u;
                List<String> actNames = new ArrayList<String>();
                for(Activity act:data){
                    actNames.add(act.getActName());
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,actNames);
                listview.setAdapter(adapter);
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
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar3);
        listview=(ListView)view.findViewById(R.id.List_view);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(mContext,Detail.class);//mContext的内容传进去
                intent.putExtra(Detail.FRUIT_NAME,data.get(position).getActName());//前一项是传出的数据到哪一活动哪一字符串常量 后一项是传入的数据是fruit类的哪一个属性
                intent.putExtra(Detail.FRUIT_TIME,data.get(position).getActTime());
                intent.putExtra(Detail.FRUIT_PLACE,data.get(position).getActLocation());
                intent.putExtra(Detail.FRUIT_ID,String.valueOf(data.get(position).getId()));
                intent.putExtra(Detail.FRUIT_NUMBER,data.get(position).getCurPeople()+"/"+data.get(position).getMaxPeople());
                intent.putExtra(Detail.FRUIT_INRODUCTION,data.get(position).getActIntroduction());
                intent.putExtra(Detail.FRUIT_IMAGE,data.get(position).getImage());
                mContext.startActivity(intent);
            }
        });
      //  ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
        //listView.setAdapter(adapter);
        //这里只是用了适配器adapter做了个例子 其实应该通过网络请求 申请义工信息 然后点击listView的响应按钮 跳转到收藏的义工信息的页面
        mSwipeRefresh=(SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh3);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
                mSwipeRefresh.setRefreshing(false);//设置刷新按钮停止转动

            }
        });
    }
}
