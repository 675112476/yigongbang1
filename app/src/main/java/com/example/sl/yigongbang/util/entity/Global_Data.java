package com.example.sl.yigongbang.util.entity;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created by abc on 2017/11/12.
 */

public class Global_Data extends com.mob.MobApplication {

    //全局数据，主要存放volunteer的信息
    public static int vol_id;
    public static String vol_phone;
    public static String vol_password;
    public static String vol_realName;
    public static Integer vol_gender;
    public static Integer vol_age;
    public static String vol_address;
    public static String vol_image;
    public static Integer vol_credit;
    public static String vol_nickName;
    public static String sessionId;


    @Override
    public String toString() {
        return "Global_Data{" +
                "vol_id=" + vol_id +
                ", vol_phone='" + vol_phone + '\'' +
                ", vol_password='" + vol_password + '\'' +
                ", vol_realName='" + vol_realName + '\'' +
                ", vol_gender=" + vol_gender +
                ", vol_age=" + vol_age +
                ", vol_address='" + vol_address + '\'' +
                ", vol_image='" + vol_image + '\'' +
                ", vol_credit=" + vol_credit +
                ", vol_nickName='" + vol_nickName + '\'' +
                '}';
    }

}
