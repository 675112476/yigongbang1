package com.example.sl.yigongbang.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Ip;
import com.example.sl.yigongbang.util.fragment.BaseFragment;
import com.example.sl.yigongbang.util.widget.MyEditText;
import com.squareup.okhttp.Request;

import org.w3c.dom.Text;

public  class SignIn extends AppCompatActivity {
    ImageView back;
    TextView LoginButton;
    MyEditText username;
    MyEditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        back = (ImageView)findViewById(R.id.back);

        username=(MyEditText)findViewById(R.id.username);
        password=(MyEditText)findViewById(R.id.password);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignIn.this,LoginMain.class);
                startActivity(it);
            }
        });
        LoginButton=(TextView)findViewById(R.id.signin2);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(networkTask).start();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    Runnable networkTask = new Runnable(){
        @Override
        public void run() {

            String userName=username.getText().toString();
            String passWord=password.getText().toString();
            OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/volunteer_login?phone="+userName+"&password="+passWord, new OkHttpClientManager.ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {
                    Log.e("3221321",e.toString());
                    Toast.makeText(SignIn.this,"异常！"+e.toString(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String u) {
                    if(u.equals("登录成功"))
                    {
                        Intent im=new Intent(SignIn.this,MainActivity.class);
                        startActivity(im);
                    }else{
                        Toast.makeText(SignIn.this,"用户名或者密码错误，请重新输入！",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    };
}
