package com.example.sl.yigongbang.util.entity;

/**
 * Created by abc on 2017/11/9.
 */

public class Ip {
    private static String a ="http://192.168.2.126:8080/";

    public static String getIp() {
        return a;
    }

    public static void setA(String a) {
        Ip.a = a;
    }
}
