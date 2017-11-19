package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Ip;
import com.example.sl.yigongbang.util.widget.MyEditText;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    ImageView back;
    TextView button;

    MyEditText phoneNumber;
    MyEditText passWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        back = (ImageView)findViewById(R.id.back);
        button=(TextView)findViewById(R.id.signin);

        phoneNumber=(MyEditText)findViewById(R.id.phonenumber);
        passWord=(MyEditText)findViewById(R.id.password);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignUp.this,LoginMain.class);
                startActivity(it);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> map=new HashMap<>();
                map.put(new String("phone"),new String (phoneNumber.getText().toString()));
                map.put(new String("password"),new String(passWord.getText().toString()));
                OkHttpClientManager.postAsyn(Ip.getIp()+"Volunteer_ssh/volunteer_register", new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {

                        Toast.makeText(SignUp.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String string) {
                        if(string.equals("注册成功")){
                            Intent it = new Intent(SignUp.this, LoginMain.class);
                            startActivity(it);
                        }
                        else{
                            Toast.makeText(SignUp.this,"注册失败，请再次尝试！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, map);
            }
        });
    }
}

