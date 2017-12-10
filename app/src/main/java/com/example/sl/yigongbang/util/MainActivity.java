package com.example.sl.yigongbang.util;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Global_Data;
import com.example.sl.yigongbang.util.entity.Ip;
import com.example.sl.yigongbang.util.entity.Volunteer;
import com.example.sl.yigongbang.util.fragment.FavouriteFragment;
import com.example.sl.yigongbang.util.fragment.HomeFragment;
import com.example.sl.yigongbang.util.fragment.RecordFragment;
import com.example.sl.yigongbang.util.widget.NoSlidingViewPaper;
import com.squareup.okhttp.Request;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private Volunteer volunteer;
    private NoSlidingViewPaper mViewPager;

    //底部菜单栏各个菜单项的点击事件处理
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home://选择主页的时候
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_record://选择信息的时候
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_favourite://选择发布的时候
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*初始化显示内容*/
        mViewPager = (NoSlidingViewPaper) findViewById(R.id.vp_main_container);
        final ArrayList<Fragment> fgLists = new ArrayList<>(3);//实现Viewpage上三个碎片的逻辑
        fgLists.add(new HomeFragment());
        fgLists.add(new RecordFragment());
        fgLists.add(new FavouriteFragment());
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);
            }

            @Override
            public int getCount() {
                return fgLists.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2); //预加载剩下两页

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        /*给底部导航栏菜单项添加点击事件*/
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                //show dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("提示")
                        .setMessage("确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();
                break;

            default:
                break;
        }
        return true;
    }
}