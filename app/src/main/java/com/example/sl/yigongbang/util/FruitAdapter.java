package com.example.sl.yigongbang.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Activity;
import com.example.sl.yigongbang.util.entity.Ip;

import java.util.List;


public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context mContext;//为了实现子项布局的复用 之后这个Context对象会用来存储子项布局 通过parent。getContext方式获取
    private List<Activity> mFruitList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView ActivityName;
        TextView ActivityTime;
        TextView AcitivityLocation;
        TextView fruitNumber;
        ImageView imageView;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;//保存最外层布局的实例，方便下面调用 其实就两个控件
            ActivityName =(TextView)view.findViewById(R.id.fruit_name);//控件实例化
            ActivityTime =(TextView)view.findViewById(R.id.fruit_time);
            AcitivityLocation =(TextView)view.findViewById(R.id.fruit_place);
            fruitNumber=(TextView)view.findViewById(R.id.fruit_number);
            imageView=(ImageView)view.findViewById(R.id.imageview1);
        }
    }
    public FruitAdapter(List<Activity> fruitList){
        mFruitList=fruitList;//将外部传入的数据转化为本类数据 数据来源可能是开头适配器注释的泛型
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);//子项布局 一定在ViewHolder的对象中(RecyclerView或者NestedScrollView下的viewholder) holder是存储了之前定义的view的 然后我们添加点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener(){//卡片布局的点击事件
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Activity fruit=mFruitList.get(position);
                Intent intent=new Intent(mContext,Detail.class);//mContext的内容传进去
                intent.putExtra(Detail.FRUIT_NAME,fruit.getActName());//前一项是传出的数据到哪一活动哪一字符串常量 后一项是传入的数据是fruit类的哪一个属性
                intent.putExtra(Detail.FRUIT_TIME,fruit.getActTime());
                intent.putExtra(Detail.FRUIT_PLACE,fruit.getActLocation());
                intent.putExtra(Detail.FRUIT_ID,String.valueOf(fruit.getId()));
                intent.putExtra(Detail.FRUIT_NUMBER,fruit.getCurPeople()+"/"+fruit.getMaxPeople());
                intent.putExtra(Detail.FRUIT_IMAGE,fruit.getImage());
                mContext.startActivity(intent);
            }
        });
        return holder;//返回内部类对象 这里实现了view的逻辑化
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Activity fruit=mFruitList.get(position);//获得当前项的fruit类 position是用来计数的
        holder.ActivityName.setText("活动名称："+fruit.getActName());//得到具体的数据
        holder.ActivityTime.setText("活动时间："+fruit.getActTime());
        holder.AcitivityLocation.setText("活动地址："+fruit.getActLocation());
        holder.imageView.setImageResource(R.drawable.ic_test_0);
        OkHttpClientManager.displayImage(holder.imageView, Ip.getIp()+"Volunteer_ssh/images/activity/"+ fruit.getImage());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();//计算集合类mFruitList的长度
    }

}
