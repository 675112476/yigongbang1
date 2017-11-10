package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sl.yigongbang.R;
import com.wx.goodview.GoodView;

public class Detail extends AppCompatActivity {

    public static final String FRUIT_NAME="fruit_name";
    public static final String FRUIT_TIME="fruit_time";
    public static final String FRUIT_PLACE="fruit_place";
    public static final String FRUIT_NUMBER="fruit_number";
    GoodView goodview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        String fruitName=intent.getStringExtra(FRUIT_NAME);//从intent中获取传入的图片以及名字 并给予我们自己定义的fruitName变量等 方便在功能逻辑中调用
        String fruitTime=intent.getStringExtra(FRUIT_TIME);
        String fruitPlace=intent.getStringExtra(FRUIT_PLACE);
        String fruitNumber=intent.getStringExtra(FRUIT_NUMBER);//这边有错 getInt
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);//Toolbar实例化
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);//Toolbar加强实例化
        TextView detail_name=(TextView)findViewById(R.id.detail_name);
        TextView detail_time=(TextView)findViewById(R.id.detail_time);
        TextView detail_place=(TextView)findViewById(R.id.detail_place);
        TextView detail_number=(TextView)findViewById(R.id.detail_number);
        detail_name.setText("活动名称:"+fruitName);//这里应该由数据库实现记录和字段的调用 我这里是初始化的示例 此处应该有服务器的数据解析后数据库调用
        detail_time.setText("活动时间:"+fruitTime);
        detail_place.setText("活动地点:"+fruitPlace);
        detail_number.setText("活动人数:"+fruitNumber);
        setSupportActionBar(toolbar);//用Toolbar替换Actionbar
        ActionBar actionBar=getSupportActionBar();//actionBar的实例 因为有返回控件
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//有返回按钮的功能
        }
        collapsingToolbarLayout.setTitle(fruitName);//标题栏图片嵌入加强版Toolbar标题栏
         goodview=new GoodView(this);
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
    public void good(View view) {
        ((ImageView) view).setImageResource(R.mipmap.good_checked);
        goodview.setText("+1");
        goodview.show(view);
    }
    public void favourite(View view){
        ((ImageView) view).setImageResource(R.mipmap.collection_checked);
        goodview.setImage(getResources().getDrawable(R.mipmap.collection_checked));
        goodview.show(view);
    }
}
