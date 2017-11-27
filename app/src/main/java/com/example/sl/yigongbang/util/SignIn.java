package com.example.sl.yigongbang.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Global_Data;
import com.example.sl.yigongbang.util.entity.Ip;
import com.example.sl.yigongbang.util.fragment.BaseFragment;
import com.example.sl.yigongbang.util.widget.MyEditText;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public  class SignIn extends AppCompatActivity {
    ImageView back;
    TextView LoginButton;
    MyEditText username;
    MyEditText password;
    private Global_Data data;
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
            }
        });
    }
    public void refresh(){
        finish();
        Intent intent=new Intent(SignIn.this,SignIn.class);
        startActivity(intent);
    }
    Runnable networkTask = new Runnable(){
        @Override
        public void run() {

            final String phone=username.getText().toString();
            String passWord=password.getText().toString();

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Ip.getIp()+"Volunteer_ssh/volunteer_login?phone="+phone+"&password="+passWord)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Toast.makeText(SignIn.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String info = response.body().string();
                    Headers headers = response.headers();

                    if(info.equals("登录成功"))
                    {
//                        Global_Data.vol_phone = phone;
                        List<String> cookies = headers.values("Set-Cookie");
                        String session = cookies.get(0);
                        String s = session.substring(0, session.indexOf(";"));
                        Global_Data.sessionId = s;
                        Log.e("-----session",s);
                        Log.e("-----body",info);
                        Intent im=new Intent(SignIn.this,MainActivity.class);
                        startActivity(im);
                    }else{
                        Looper.prepare();
                        Toast.makeText(SignIn.this,"用户名或者密码错误，请重新输入！",Toast.LENGTH_SHORT).show();
                        refresh();
                        Looper.loop();
                    }
                }
            });
/*

            OkHttpClientManager.getAsyn(Ip.getIp()+"Volunteer_ssh/volunteer_login?phone="+phone+"&password="+passWord, new OkHttpClientManager.ResultCallback<Response>() {
                @Override
                public void onError(Request request, Exception e) {
                    Log.e("3221321",e.toString());
                    Toast.makeText(SignIn.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Response response) {
                    String headers = response.headers().toString();
                    String info = response.body().toString();
                    if(info.equals("登录成功"))
                    {
                        Global_Data.vol_phone = phone;
                        Log.e("-----headers",headers);
                        Log.e("-----vol_phone",Global_Data.vol_phone);
                        Intent im=new Intent(SignIn.this,MainActivity.class);
                        startActivity(im);
                    }else{
                        Toast.makeText(SignIn.this,"用户名或者密码错误，请重新输入！",Toast.LENGTH_SHORT).show();
                    }
                }
            });

*/

        }
    };
}
