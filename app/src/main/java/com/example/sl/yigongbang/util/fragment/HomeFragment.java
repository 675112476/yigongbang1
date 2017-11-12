package com.example.sl.yigongbang.util.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.FruitAdapter;
import com.example.sl.yigongbang.util.Information;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.PersonalInfo;
import com.example.sl.yigongbang.util.Setting;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private EditText searchEdit;
    private EditText editText;
    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout mDrawerLayout;
    private CircleImageView circleImageView;
    private List<Activity>ActivityList=new ArrayList<Activity>();
    private FruitAdapter adapter;
    private RecyclerView recyclerView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    protected void getDataFromServer() {
        OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/activity_getAll",
                new OkHttpClientManager.ResultCallback<List<Activity>>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        Log.e("--------Home错误",e.toString());
                    }
                    @Override
                    public void onResponse(List<Activity> us)
                    {
                       ActivityList=us;
                        for(Activity attribute : ActivityList) {

                            Log.e("--------maxpeople", "onResponse: "+attribute.getMaxPeople() );
                        }
                        adapter=new FruitAdapter(ActivityList);
                        recyclerView.setAdapter(adapter);//适配器实例与recyclerView控件关联
                    }
                });
    }

    public String[] splitString(String string){

        String[] array = string.split(",");
        return array;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar)view. findViewById(R.id.toolbar);//ToolBar实例化 逻辑化
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);//通过setSupportActionBar方法引用ToolBar实例
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);//实例化滑动菜单控件
        NavigationView navView = (NavigationView)view. findViewById(R.id.nav_view);//滑动菜单菜单具体控件实例化
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//为了显示导航按钮图标 这里要改一下了
        }
        navView.setCheckedItem(R.id.nav_personal);//call菜单默认选中， 这里我设置成了个人信息
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){//监听滑动菜单控件 在这里给Item添加逻辑
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {//点击任意侧滑菜单图标就会回调这个方法
                switch (item.getItemId()){
                    case R.id.nav_personal://点击个人信息的item时候
                        Intent intentpersonal=new Intent(getActivity(),PersonalInfo.class);
                        startActivity(intentpersonal);
                        break;
                    case R.id.nav_setting://点击setting的item时候
                        Intent  intentsetting=new Intent(getActivity(),Setting.class);
                        startActivity(intentsetting);
                        getActivity().finish();
                        break;
                }
                return true;
            }
        });
        circleImageView=(CircleImageView) view.findViewById(R.id.icon_image);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);//悬浮按钮实例化 逻辑化
            fab.setVisibility(View.INVISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Information.class);
                    startActivity(intent);
                }
            });
        getDataFromServer();

        for(Activity attribute : ActivityList) {

            Log.e("------!!!!!!!!", "onResponse: "+attribute.getActName() );
        }
        //滚动控件/卡片布局逻辑
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);//网格布局 有两列
        recyclerView.setLayoutManager(layoutManager);//网格布局
       // adapter=new FruitAdapter(ActivityList);
        //recyclerView.setAdapter(adapter);//适配器实例与recyclerView控件关联
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);


        //获取本地的图片
        for (int position = 0; position < 3; position++) {
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
        }

        //开始自动翻页
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(2000);
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                //设置指示器的方向（左、中、右）
                //设置点击监听事件
                //设置手动影响（设置了该项无法手动切换）
        //设置翻页的效果，不需要翻页效果可用不设
        //setPageTransformer(Transformer.DefaultTransformer);   // 集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。

    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar, menu);//引入toolbar标题栏的实例
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home://当点击标题栏中的id名为home的按钮时
                mDrawerLayout.openDrawer(GravityCompat.START);//点击打开右滑动菜单
                break;
            case R.id.backup://当点击标题栏中的id名为backup的按钮时
                Toast.makeText(getActivity(), "You clicked backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(getActivity(), "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(getActivity(), "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
    }

    //为了方便改写，来实现复杂布局的切换
    private class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}


