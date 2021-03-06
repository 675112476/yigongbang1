package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.entity.Global_Data;
import com.example.sl.yigongbang.util.widget.MyTextView;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginMain extends AppCompatActivity {

    TextView signin1;
    TextView signup1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);



        signin1 = (TextView)findViewById(R.id.sign_in_1);
        signup1 = (TextView)findViewById(R.id.sign_up_1);


        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(LoginMain.this,SignIn.class);
                startActivity(it1);
            }
        });


        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(LoginMain.this,SignUp.class);
                startActivity(it1);
            }
        });

    }
    private void submitInfo(String country, String phone) {
        Random r = new Random();
        String uid = Math.abs(r.nextInt()) + "";
        String nickName = "设置个昵称";
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);// 提交用户信息，在监听中返回
    }
}

