package com.example.sl.yigongbang.util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Information extends AppCompatActivity {

    Button act_release;
    EditText act_name;
    EditText act_time;
    EditText act_location;
    EditText act_person;
    EditText act_descript;
    ImageView act_image;
    CircleImageView img_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        act_release =(Button)findViewById(R.id.fabu);
        act_name=(EditText)findViewById(R.id.act_name);
        act_time=(EditText)findViewById(R.id.act_time);
        act_location=(EditText)findViewById(R.id.act_location);
        act_person=(EditText)findViewById(R.id.act_person);
        act_descript=(EditText)findViewById(R.id.act_descript);
        act_image = (ImageView) findViewById(R.id.act_image);
        img_add = (CircleImageView) findViewById(R.id.img_add);
        act_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToServer();
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void sendDataToServer(){
        Map<String,String> map=new HashMap<String,String>();
        map.put("actName",act_name.getText().toString());
        map.put("actTime",act_time.getText().toString());
        map.put("actLocation",act_location.getText().toString());
        map.put("actIntroduction",act_descript.getText().toString());
        map.put("maxPeople",act_person.getText().toString());
        OkHttpClientManager.postAsyn(Ip.getIp() + "Volunteer_ssh/activity_release", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(Information.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String string) {

                if(string .equals("success")){
                    Toast.makeText(Information.this,"发布活动成功",Toast.LENGTH_SHORT);
                }else
                    if(string.equals("failed")){
                        Toast.makeText(Information.this,"发布活动失败",Toast.LENGTH_SHORT);
                        Log.e("---Information_string",string);
                    }else{
                        Log.e("---Information_string",string);
                        Toast.makeText(Information.this,"Error",Toast.LENGTH_SHORT);
                    }
            }
        },map);
    }
}
