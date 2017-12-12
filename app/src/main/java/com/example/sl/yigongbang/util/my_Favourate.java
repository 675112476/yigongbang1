package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

public class my_Favourate extends AppCompatActivity {

    private List<Activity> data;
    private ListView listview;
    private SwipeRefreshLayout mSwipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__favourate);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        listview=(ListView)findViewById(R.id.List_view);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(my_Favourate.this,Detail.class);//mContext的内容传进去
                intent.putExtra(Detail.FRUIT_NAME,data.get(position).getActName());//前一项是传出的数据到哪一活动哪一字符串常量 后一项是传入的数据是fruit类的哪一个属性
                intent.putExtra(Detail.FRUIT_TIME,data.get(position).getActTime());
                intent.putExtra(Detail.FRUIT_PLACE,data.get(position).getActLocation());
                intent.putExtra(Detail.FRUIT_ID,String.valueOf(data.get(position).getId()));
                intent.putExtra(Detail.FRUIT_NUMBER,data.get(position).getCurPeople()+"/"+data.get(position).getMaxPeople());
                intent.putExtra(Detail.FRUIT_INRODUCTION,data.get(position).getActIntroduction());
                intent.putExtra(Detail.FRUIT_IMAGE,data.get(position).getImage());
                intent.putExtra("curnum",String.valueOf(data.get(position).getCurPeople()));
                intent.putExtra("maxnum",String.valueOf(data.get(position).getMaxPeople()));
                startActivity(intent);
            }
        });
        //这里只是用了适配器adapter做了个例子 其实应该通过网络请求 申请义工信息 然后点击listView的响应按钮 跳转到收藏的义工信息的页面
        mSwipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh3);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
                mSwipeRefresh.setRefreshing(false);//设置刷新按钮停止转动

            }
        });
        getDataFromServer();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(my_Favourate.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return  true;
    }

    protected void getDataFromServer() {
        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getCollected",new OkHttpClientManager.ResultCallback<List<Activity>>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("--------Favourite错误",e.toString());
                Toast.makeText(my_Favourate.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<Activity> u) {
                data = u;
                List<String> actNames = new ArrayList<String>();
                for(Activity act:data){
                    actNames.add(act.getActName());
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(my_Favourate.this,android.R.layout.simple_list_item_1,actNames);
                listview.setAdapter(adapter);
            }
        });
    }

    public String[] splitString(String string){

        string=string.substring(1,string.length()-1);
        String[] array = string.split(", ");
        return array;
    }
}
