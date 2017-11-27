package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Global_Data;
import com.example.sl.yigongbang.util.entity.Ip;
import com.example.sl.yigongbang.util.widget.MyEditText;
import com.example.sl.yigongbang.util.widget.MyTextView;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignUp extends AppCompatActivity {

    ImageView back;
    TextView button;

    com.example.sl.yigongbang.util.widget.MyEditText phone;
    com.example.sl.yigongbang.util.widget.MyEditText password;
    com.example.sl.yigongbang.util.widget.MyEditText yanzhengma;

    Button send_code;
    boolean checked=false;
    boolean get_code=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        back = (ImageView)findViewById(R.id.back);
        button=(TextView)findViewById(R.id.signin);

        phone=(MyEditText)findViewById(R.id.phone);
        password=(MyEditText)findViewById(R.id.password);
        yanzhengma=(MyEditText)findViewById(R.id.yanzhenma);
        send_code=(Button)findViewById(R.id.send_code);


        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                switch (event) {
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.e("验证成功","-----");
                            if(password.getText().toString().trim().equals("")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUp.this,"请输入密码",Toast.LENGTH_SHORT);
                                    }
                                });

                            }else{
                                new Thread(netWorkTask).start();
                            }

                        } else {
                            Log.e("验证失败","-----");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignUp.this,"请输入正确的验证码",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        break;
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.e("获取验证成功","-----");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

//                                    if(yanzheng){
//                                        Toast.makeText(SignUp.this,"号码已经注册，请直接使用用户名密码登陆。",Toast.LENGTH_SHORT).show();
//                                    }else{
                                        Toast.makeText(SignUp.this,"发送成功,注意查收!",Toast.LENGTH_SHORT).show();
                                        get_code=true;
//                                    }

                                }
                            });
                        } else {
                            Log.e("获取验证失败","-----");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignUp.this,"发送失败,请检查网络和您的手机号是否正确！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        break;
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignUp.this,LoginMain.class);
                startActivity(it);

            }
        });
        send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.getVerificationCode("86", new String(phone.getText().toString()));//发送短信验证码到手机号  86表示的是中国
            }
        });
        Log.e("----get_code",String.valueOf(get_code));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("----checked",String.valueOf(checked));
                if(password.getText().toString()!=null) {
                    SMSSDK.submitVerificationCode("86", new String(phone.getText().toString()), new String(yanzhengma.getText().toString()));//提交验证码  在eventHandler里面查看验证结果
                }else{
                    Toast.makeText(SignUp.this,"请输入密码",Toast.LENGTH_SHORT);
                }
            }
        });

    }
    public void refresh(){
        finish();
        Intent intent=new Intent(SignUp.this,SignUp.class);
        startActivity(intent);
    }
    Runnable netWorkTask=new Runnable() {
        @Override
        public void run() {
            OkHttpClient client=new OkHttpClient();
            FormEncodingBuilder builder = new FormEncodingBuilder();
            builder.add(new String("phone"),new String(phone.getText().toString()));
            builder.add(new String("password"),new String(password.getText().toString()));
            RequestBody requestBody = builder.build();
            Request request=new Request.Builder().url(Ip.getIp() + "Volunteer_ssh/volunteer_register").post(requestBody).build();
            try {
                Response response=client.newCall(request).execute();
                String string=response.body().string();

                Log.e("----string",string);
                if(string.equals("注册成功")){
                    Looper.prepare();
                    Toast.makeText(SignUp.this,"注册成功请使用账号密码登录",Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(SignUp.this, SignIn.class);
                    startActivity(it);
                    Looper.loop();

                } else
                if(string.equals("用户名已存在"))
                {
                    Looper.prepare();
                    Toast.makeText(SignUp.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                    refresh();
                    Looper.loop();
                }
                else{
                    Looper.prepare();
                    Toast.makeText(SignUp.this,"注册失败，请再次尝试！",Toast.LENGTH_SHORT).show();
                    refresh();
                    Looper.loop();
                }
            } catch (IOException e) {
                Toast.makeText(SignUp.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
                refresh();
            }
        }
    };
}

