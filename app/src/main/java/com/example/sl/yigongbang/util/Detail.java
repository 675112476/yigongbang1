package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Global_Data;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;
import com.wx.goodview.GoodView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Detail extends AppCompatActivity {

    public static final String FRUIT_ID="fruit_id";
    public static final String FRUIT_NAME="fruit_name";
    public static final String FRUIT_TIME="fruit_time";
    public static final String FRUIT_PLACE="fruit_place";
    public static final String FRUIT_NUMBER="fruit_number";
    public static final String FRUIT_INRODUCTION="fruit_introduction";
    public static final String FRUIT_IMAGE="fruit_image";
    public static int actId;
    GoodView goodview;
    Button Join;
    boolean isCollected;
    boolean isJoined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        String fruitId=intent.getStringExtra(FRUIT_ID);
        actId=Integer.valueOf(fruitId);//从intent中获取传入的图片以及名字 并给予我们自己定义的fruitName变量等 方便在功能逻辑中调用
        String fruitName=intent.getStringExtra(FRUIT_NAME);
        String fruitTime=intent.getStringExtra(FRUIT_TIME);
        String fruitPlace=intent.getStringExtra(FRUIT_PLACE);
        String fruitNumber=intent.getStringExtra(FRUIT_NUMBER);
        String fruitIntroduction=intent.getStringExtra(FRUIT_INRODUCTION);
        String fruitImage=intent.getStringExtra(FRUIT_IMAGE);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);//Toolbar实例化
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);//Toolbar加强实例化
        TextView detail_name=(TextView)findViewById(R.id.detail_name);
        TextView detail_time=(TextView)findViewById(R.id.detail_time);
        TextView detail_place=(TextView)findViewById(R.id.detail_place);
        TextView detail_number=(TextView)findViewById(R.id.detail_number);
        TextView detail_introduction=(TextView)findViewById(R.id.detail_introduction);
        ImageView detail_image = (ImageView) findViewById(R.id.detail_image);
        detail_name.setText("活动名称:"+fruitName);//这里应该由数据库实现记录和字段的调用 我这里是初始化的示例 此处应该有服务器的数据解析后数据库调用
        detail_time.setText("活动时间:"+fruitTime);
        detail_place.setText("活动地点:"+fruitPlace);
        Log.e("---活动介绍",fruitIntroduction);
        detail_introduction.setText("活动介绍："+fruitIntroduction);
        detail_number.setText("活动人数:"+fruitNumber);
        OkHttpClientManager.displayImage(detail_image,Ip.getIp()+"Volunteer_ssh/images/activity/"+ fruitImage);
        setSupportActionBar(toolbar);//用Toolbar替换Actionbar
        ActionBar actionBar=getSupportActionBar();//actionBar的实例 因为有返回控件
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//有返回按钮的功能
        }
        collapsingToolbarLayout.setTitle(fruitName);//标题栏图片嵌入加强版Toolbar标题栏
        goodview=new GoodView(this);
        ImageView view=(ImageView)findViewById(R.id.favouriteview);
        get_collected(view);
        Join=(Button)findViewById(R.id.join);
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(fruitTime);
            SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
            Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
            if(date.getTime()<= curDate.getTime()){
                //活动已过期
                Join.setText("活动已结束");
                Join.setClickable(false);
                Join.setBackgroundColor(Color.parseColor("#ffd5d5d5"));
            }
        else{
                get_joined(Join);
                Join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isJoined==true){
                            cancel_join((Button) view);
                        }else
                        if(isJoined==false){
                            join((Button)view);
                        }
                    }
                });
            }
        }catch (Exception e){
            Log.e("-----获取事件失败",e.toString());
        }

    }

    public void get_joined(final Button button){
        //判断是否参加
        OkHttpClientManager.getAsyn(Ip.getIp() + "Volunteer_ssh/record_isJoined?actId="+actId, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(Detail.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String string) {
                Log.e("----",string);
                if(string.equals("true")){
                    //Toast.makeText(Detail.this,"isjoined！",Toast.LENGTH_SHORT).show();
                    button.setText("取消报名");
                    isJoined=true;
                }else if(string.equals("false"))
                {
                    //Toast.makeText(Detail.this,"unjoined！",Toast.LENGTH_SHORT).show();
                    isJoined=false;
                }
            }
        });
    }
    public void join(final Button button){
        //报名的请求
        OkHttpClientManager.getAsyn(Ip.getIp() + "Volunteer_ssh/record_join?actId="+actId, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(Detail.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String string) {
                Log.e("报名",string);
                if(string.equals("joined")){
                    button.setText("取消报名");
                    isJoined=true;
                    Toast.makeText(Detail.this,"报名成功！",Toast.LENGTH_SHORT).show();

                }else if(string.equals("overpeople")){
//                    button.setText("人数已满");
//                    button.setClickable(false);
//                    button.setBackgroundColor(Color.parseColor("#ffd5d5d5"));
                    Toast.makeText(Detail.this,"人数已满！",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Detail.this,"报名失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void cancel_join(final Button button){
        //取消报名
        OkHttpClientManager.getAsyn(Ip.getIp() + "Volunteer_ssh/record_cancelJoin?actId="+actId, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(Detail.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String string) {
                if(string.equals("canceljoined")){
                    button.setText("我要报名");
                    isJoined=false;
                    Toast.makeText(Detail.this,"取消报名成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Detail.this,"取消报名失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//标题栏菜单按钮的点击事件 包括侧滑的 隐藏的
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//    public void good(View view) {
//        ((ImageView) view).setImageResource(R.mipmap.good_checked);
//        goodview.setText("+1");
//        goodview.show(view);
//
//    }
    public void favourite(View view){
        if(isCollected==true){
            cancel_collect(view);
        }else if(isCollected==false){
            collect(view);
        }
    }
    public void get_collected(View view){
        //判断是否收藏
        final View view1=view;
        OkHttpClientManager.getAsyn(Ip.getIp() + "Volunteer_ssh/record_isCollected?actId="+actId, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(Detail.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String string) {
                Log.e("----",string);
                if(string.equals("true")){
                    //Toast.makeText(Detail.this,"iscollected！",Toast.LENGTH_SHORT).show();
                    ((ImageView) view1).setImageResource(R.mipmap.collection_checked);
                    goodview.setImage(getResources().getDrawable(R.mipmap.collection_checked));
                    goodview.show(view1);
                    isCollected=true;

                }else if(string.equals("false"))
                {
                    //Toast.makeText(Detail.this,"uncollected！",Toast.LENGTH_SHORT).show();
                    ((ImageView) view1).setImageResource(R.mipmap.collection);
                    goodview.setImage(getResources().getDrawable(R.mipmap.collection));
                    goodview.show(view1);
                    isCollected=false;
                }

            }
        });
    }
    public void collect(View view){
        //收藏的请求
        final View view1=view;
        Log.e("---vol_id",""+actId);
        OkHttpClientManager.getAsyn(Ip.getIp() + "Volunteer_ssh/record_collect?actId="+actId, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(Detail.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String string) {
                if(string.equals("collected")){
                    Toast.makeText(Detail.this,"收藏成功！",Toast.LENGTH_SHORT).show();
                    isCollected=true;
                    ((ImageView) view1).setImageResource(R.mipmap.collection_checked);
                    goodview.setImage(getResources().getDrawable(R.mipmap.collection_checked));
                }else{
                    Toast.makeText(Detail.this,"收藏失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void cancel_collect(View view){
        //取消收藏
        final View view1=view;
        OkHttpClientManager.getAsyn(Ip.getIp() + "Volunteer_ssh/record_cancelCollect?actId="+actId, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(Detail.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String string) {
                if(string.equals("cancelcollected")){
                    Toast.makeText(Detail.this,"取消收藏成功！",Toast.LENGTH_SHORT).show();
                    isCollected=false;
                    ((ImageView) view1).setImageResource(R.mipmap.collection);
                    goodview.setImage(getResources().getDrawable(R.mipmap.collection));
                }else{
                    Toast.makeText(Detail.this,"取消收藏失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
