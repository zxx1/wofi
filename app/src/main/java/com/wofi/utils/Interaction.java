package com.wofi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wofi.Activity.MainActivity;
import com.wofi.Activity.RepairActivity;
import com.wofi.constants.Constants;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wofi.application.MyApplication.bicycleXYlist;
import static com.wofi.application.MyApplication.cash;
import static com.wofi.application.MyApplication.feedback;
import static com.wofi.application.MyApplication.firstinitmarker;
import static com.wofi.application.MyApplication.flag;
import static com.wofi.application.MyApplication.getbicycle;
import static com.wofi.application.MyApplication.journeyList;
import static com.wofi.application.MyApplication.rechargebillList;
import static com.wofi.application.MyApplication.responceData;
import static com.wofi.application.MyApplication.responceData1;
import static com.wofi.application.MyApplication.responceData2;
import static com.wofi.application.MyApplication.responceData4;
import static com.wofi.application.MyApplication.responceData5;
import static com.wofi.application.MyApplication.userinfo;


public class Interaction {
    /**
     * 用户注册
     * LoginActivity line199
     */
    public static void register(final String username){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtils.sendOkHttpRequest(Constants.LOGIN_URL+username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }


    /**
     * 充值记录
     * GsonService initRechagebill(),parseJSONWithGSONrecharge()删除;
     * GsonService flag 移至MyApplication     public static boolean flag=false;
     * GsonService onStartcommand()添加:flags = START_STICKY,  flag=false;
     * GsonService MyTask有改动;
     */
    public static void queryRecharge(final String username){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long begin= System.currentTimeMillis();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.RECHAGEBILL_URL+username)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    responceData=response.body().string();
                    parseJSONWithGSONrecharge(responceData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long end= System.currentTimeMillis();
                Log.e("线程2", String.valueOf(end-begin)+"ms");
            }


        });
        t.start();
        while(flag){
            t.interrupt();
        }
    }
    private static void parseJSONWithGSONrecharge(String jsonData)
    {
        Gson gson=new Gson();
        rechargebillList=gson.fromJson(jsonData,new TypeToken<List<Rechargebill>>(){}.getType());
        Collections.reverse(rechargebillList);
        for(Rechargebill rechargebill:rechargebillList){
            Log.e("充值","money is"+rechargebill.getRechargeAmount());
        }
    }


    /**
     * 用户充值
     * RechargeActivity line 79、80注释掉;
     * RechargeActivity line 65 onClick()内有改动;
     */
    public static void recharge(final String a, final String username){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtils.sendOkHttpRequest(Constants.RECHAGE_URL+a+"/"+username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * 用户信息
     * GsonService initUserInfo(),parseJSONWithGSONuser()删除;
     * GsonService MyTimeTask改动;
     * MainActivity initUserInfo()删除,onCreate line 132后有改动
     */
    public static void userInfo(final String username){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.USERINFO_URL+username)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    responceData2=response.body().string();
                    parseJSONWithGSONuser(responceData2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        while(flag){
            t.interrupt();
        }
    }
    private static void parseJSONWithGSONuser(String jsonData)
    {
        Gson gson=new Gson();
        userinfo=gson.fromJson(jsonData,new TypeToken<UserInfo>(){}.getType());
        Log.e("余额","money is"+userinfo.getUserAccount());

    }


    /**
     * 提交押金
     * UserCashActivity line 68:onClick()中有改动
     */
    public static void submitUserCash(final String username){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtils.sendOkHttpRequest(Constants.CASH_URL+username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * 获取押金
     * GsonService initCash()删除，MyTask改动
     * MainActivity initCash()删除，onCreate line 132后改动
     */
    public static void getUserCash(final String username){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long begin= System.currentTimeMillis();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.GET_CASH_URL+username)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    cash=response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long end= System.currentTimeMillis();
                Log.e("线程1", String.valueOf(end-begin)+"ms");
            }
        });
        t.start();
        while(flag){
            t.interrupt();
        }
    }

    /**
     * 退押金
     * ReturnCashActivity line 58:onClick()改动
     */
    public static void returnUserCash(final String username){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtils.sendOkHttpRequest(Constants.RETURN_CASH_URL+username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * 用户反馈
     * RepairActivity initFeedback()删除,
     * RepairActivity line 92:onClick改动
     */
    public static void userFeedback(final String Title, final String Contenttext, final String Remark, final String BicycleId, final String username){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sTitle= URLEncoder.encode(Title,"utf-8");
                    String sContenttext= URLEncoder.encode(Contenttext+",备注:"+Remark,"utf-8");
                    HttpUtils.sendOkHttpRequest(Constants.USERFEEDBACK_URL+sTitle+"/"+sContenttext+"/"+BicycleId+"/"+username);
                    feedback=HttpUtils.getResponseData();
                    Log.e("反馈", feedback);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * 附近车辆
     * MainActivity initBicycle(),parseJSONWithGSONbicycle()删除
     * 添加initMarker()
     * MyApplication添加 public static boolean getbicycle=false; public static boolean firstinitmarker=false;
     * MainActivity OnLocationChanged改动
     * MainActivity新增刷新按钮,在initLayout中注册,onClick()添加点击事件;
     */
    public static void bicycleLocation(final double lon, final double lat){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.BICYCLE_URL+ String.valueOf(lon)+"/"+ String.valueOf(lat)+"/end")
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    responceData4=response.body().string();
                    parseJSONWithGSONbicycle(responceData4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getbicycle=true;
                //Log.e("时间", String.valueOf(System.currentTimeMillis() - time));
            }
        });
        t.start();
    }
    private static void parseJSONWithGSONbicycle(String jsonData)
    {
        Gson gson=new Gson();
        bicycleXYlist=gson.fromJson(jsonData,new TypeToken<List<BicycleXY>>(){}.getType());
        for(BicycleXY bicycleXY:bicycleXYlist) {
            Log.e("长度",String.valueOf(bicycleXYlist.size()));
            //Log.e("位置", "latlon is" + bicycleXY.getBicycleCurrentX() + " " + bicycleXY.getBicycleCurrentY());
            Log.e("状态",  bicycleXY.getBicycleId() + " 的状态 is："+ String.valueOf(bicycleXY.getBicycleStatement() + "，latlon is" + bicycleXY.getBicycleCurrentX() + " " + bicycleXY.getBicycleCurrentY()));
        }
    }

    /**
     * 借车
     */
    public static void borrowBicycle(final String bicycleId, final String username){
        Thread t=new Thread(){
            @Override
            public void run() {
                try {
                    HttpUtils.sendOkHttpRequest(Constants.BORROWBICYCLE_URL+ String.valueOf(bicycleId)+"/"+username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


    /**
     * 租借记录
     */
    public static void queryBorrow(final String username)
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long begin= System.currentTimeMillis();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.JOURNEY_URL+username)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    responceData1=response.body().string();
                    parseJSONWithGSONjourney(responceData1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long end= System.currentTimeMillis();
                Log.e("线程1", String.valueOf(end-begin)+"ms");
            }
        });
        t.start();
    }
    private static void parseJSONWithGSONjourney(String jsonData)
    {
        Gson gson=new Gson();
        journeyList=gson.fromJson(jsonData,new TypeToken<List<Journey>>(){}.getType());
        Collections.reverse(journeyList);
        for(Journey journey:journeyList){
            Log.e("行程","money is"+journey.getCost());
        }


    }
    public static void returnBicycle(final String bicycleId, final String username, final double lon, final double lat, final String cost)
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long begin= System.currentTimeMillis();
                try {
                    HttpUtils.sendOkHttpRequest(Constants.RETURNBICYCLE_URL+bicycleId+"/"+username+"/"+ String.valueOf(lon)+"/"+ String.valueOf(lat)+"/"+cost+"/"+"end");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long end= System.currentTimeMillis();
                Log.e("线程1", String.valueOf(end-begin)+"ms");
            }
        });
        t.start();
    }
}
