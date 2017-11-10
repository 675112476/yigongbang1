package com.example.sl.yigongbang.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.fragment.FavouriteFragment;
import com.example.sl.yigongbang.util.fragment.HomeFragment;
import com.example.sl.yigongbang.util.fragment.RecordFragment;
import com.example.sl.yigongbang.util.widget.NoSlidingViewPaper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
}