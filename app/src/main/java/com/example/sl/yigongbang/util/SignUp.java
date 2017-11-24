package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.os.Handler;
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
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

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
                        } else {
                            Log.e("验证失败","-----");
                        }
                        break;
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.e("获取验证成功","-----");
                            checked=true;
                        } else {
                            Log.e("获取验证失败","-----");
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.submitVerificationCode("86",new String(phone.getText().toString()), new String(yanzhengma.getText().toString()));//提交验证码  在eventHandler里面查看验证结果
                Map<String,String> map=new HashMap<>();
                map.put(new String("phone"),new String(phone.getText().toString()) );
                map.put(new String("password"),new String(password.getText().toString()));
                if(checked=true){
                    OkHttpClientManager.postAsyn(Ip.getIp()+"Volunteer_ssh/volunteer_register", new OkHttpClientManager.ResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {

                            Toast.makeText(SignUp.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String string) {
                            if(string.equals("注册成功")){
                                Toast.makeText(SignUp.this,"注册成功，请使用电话号码和密码重新登陆！",Toast.LENGTH_LONG).show();
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Intent it = new Intent(SignUp.this, LoginMain.class);
                                startActivity(it);
                            }
                            else{
                                Toast.makeText(SignUp.this,"注册失败，请再次尝试！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, map);
                }

            }
        });
    }


}

