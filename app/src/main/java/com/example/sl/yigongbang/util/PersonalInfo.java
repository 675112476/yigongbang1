package com.example.sl.yigongbang.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Global_Data;
import com.example.sl.yigongbang.util.entity.Ip;
import com.example.sl.yigongbang.util.fragment.HomeFragment;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

public class PersonalInfo extends AppCompatActivity {

    private EditText realName;
    private EditText age;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;
    private EditText address;
    private Button updateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        realName = (EditText) findViewById(R.id.realName);
        age = (EditText) findViewById(R.id.age);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        address = (EditText) findViewById(R.id.address);
        updateInfo = (Button) findViewById(R.id.updateInfo);

        realName.setText(Global_Data.vol_realName);
        age.setText(Integer.toString(Global_Data.vol_age));
        if(Global_Data.vol_gender==0){
            male.setChecked(true);
        }else if(Global_Data.vol_gender==1){
            female.setChecked(true);
        }
        address.setText(Global_Data.vol_address);
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(PersonalInfo.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return  true;
    }

    private void saveInfo(){
        String vol_realName = realName.getText().toString();
        Integer vol_age = Integer.parseInt(age.getText().toString());
        Integer vol_gender = null;
        if(male.isChecked()){
            vol_gender = 0;
        }else if(female.isChecked()){
            vol_gender = 1;
        }
        String vol_address = address.getText().toString();

        // update 请求
        Map<String,String> map=new HashMap<>();
        map.put(new String("realName"),vol_realName);
        map.put(new String("age"),String.valueOf(vol_age));
        map.put(new String("gender"), String.valueOf(vol_gender));
        map.put(new String("address"),vol_address);
        OkHttpClientManager.postAsyn(Ip.getIp() + "/Volunteer_ssh/volunteer_updateInfo", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(PersonalInfo.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String string) {

                if(string.equals("update")){
                    finish();
//                    Intent intent=new Intent(PersonalInfo.this, HomeFragment.class);
//                    startActivity(intent);
                }
                else
                {
                    Log.e("---提交",string);
                }
            }
        },map);

        //保存全局数据
        Global_Data.vol_realName = vol_realName;
        Global_Data.vol_age = vol_age;
        Global_Data.vol_gender = vol_gender;
        Global_Data.vol_address = vol_address;
    }
}
