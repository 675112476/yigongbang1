package com.example.sl.yigongbang.util.entity;

import android.app.Application;

/**
 * Created by abc on 2017/11/12.
 */

public class Global_Data extends Application {

    //全局数据，主要存放volunteer的信息
    private int vol_id;
    private String vol_phone;
    private String vol_password;
    private String vol_realName;
    private Integer vol_gender;
    private Integer vol_age;
    private String vol_address;
    private String vol_image;
    private Integer vol_credit;
    private String vol_nickName;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getVol_id() {
        return vol_id;
    }

    public void setVol_id(int vol_id) {
        this.vol_id = vol_id;
    }

    public String getVol_phone() {
        return vol_phone;
    }

    public void setVol_phone(String vol_phone) {
        this.vol_phone = vol_phone;
    }

    public String getVol_password() {
        return vol_password;
    }

    public void setVol_password(String vol_password) {
        this.vol_password = vol_password;
    }

    public String getVol_realName() {
        return vol_realName;
    }

    public void setVol_realName(String vol_realName) {
        this.vol_realName = vol_realName;
    }

    public Integer getVol_gender() {
        return vol_gender;
    }

    public void setVol_gender(Integer vol_gender) {
        this.vol_gender = vol_gender;
    }

    public Integer getVol_age() {
        return vol_age;
    }

    public void setVol_age(Integer vol_age) {
        this.vol_age = vol_age;
    }

    public String getVol_address() {
        return vol_address;
    }

    public void setVol_address(String vol_address) {
        this.vol_address = vol_address;
    }

    public String getVol_image() {
        return vol_image;
    }

    public void setVol_image(String vol_image) {
        this.vol_image = vol_image;
    }

    public Integer getVol_credit() {
        return vol_credit;
    }

    public void setVol_credit(Integer vol_credit) {
        this.vol_credit = vol_credit;
    }

    public String getVol_nickName() {
        return vol_nickName;
    }

    public void setVol_nickName(String vol_nickName) {
        this.vol_nickName = vol_nickName;
    }

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
