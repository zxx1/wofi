package com.wofi.application;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.wofi.utils.BicycleXY;
import com.wofi.utils.Journey;
import com.wofi.utils.Rechargebill;
import com.wofi.utils.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;
    public static MyApplication getInstance() {
        return myApplication;
    }
    public static List<Journey> journeyList=new ArrayList<>();//获取行程记录

    public static List<Rechargebill> rechargebillList=new ArrayList<>();//钱包记录

    public static List<BicycleXY> bicycleXYlist=new ArrayList<>();//车辆信息

    public static String feedback;  //用户反馈返回信息


    public static UserInfo userinfo=new UserInfo();//用户信息
    public static String responceData1;
    public static String responceData2;
    public static String responceData;
    public static String responceData4;
    public static String responceData5;

    public static String cash;
    public static boolean flag=false;
    public static boolean getbicycle=false;
    public static boolean firstinitmarker=false;

    public static long startTime=0;
    public static long endTime=0;
    public static String bicycleId;





    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        ZXingLibrary.initDisplayOpinion(this);
    }
}
