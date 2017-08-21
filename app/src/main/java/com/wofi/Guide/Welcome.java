package com.wofi.Guide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import com.wofi.Activity.LoginActivity;
import com.wofi.Activity.MainActivity;
import com.wofi.R;
import com.wofi.constants.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wofi.application.MyApplication.cash;

public class Welcome extends Activity {
    private static final int TIME = 1500;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final int GO_LOGIN = 1002;
    private boolean isFirstIn = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
                case GO_LOGIN:
                    goLogin();
                    break;
            }
        }
    };

    public Welcome() throws PackageManager.NameNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        setContentView(R.layout.activity_welcome);
        init();
    }

    public void init(){
        //执行成功的话，会将数据保存在homeTest.xml文件中
        SharedPreferences preferences = getSharedPreferences("homeTest",MODE_PRIVATE);
        //第一个参数是key，第二个参数是默认值
        isFirstIn = preferences.getBoolean("isFirstIn",true);
        SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
        String Token=sp.getString("token","").trim();
        //Log.e("哈克哈就开始的",Token);
        //Log.e("sad as as",getLocalMacAddress());

        if (!isFirstIn){
            if(Token.equals(getLocalMacAddress().trim())) {
                handler.sendEmptyMessageDelayed(GO_HOME, TIME);

            }
            else {
                handler.sendEmptyMessageDelayed(GO_LOGIN,TIME);
            }
        }
        else {
            handler.sendEmptyMessageDelayed(GO_GUIDE,TIME);
            //利用edit()方法获取Editor对象
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.commit();
        }
    }


    private void goHome(){
        Intent intent = new Intent(Welcome.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void goGuide(){
        Intent intent = new Intent(Welcome.this,Guide.class);
        startActivity(intent);
        finish();
    }
    private void goLogin(){
        Intent intent = new Intent(Welcome.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }
    private void initCash()
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long begin=System.currentTimeMillis();
                SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
                String username=sp.getString("Username","").trim();
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
                long end=System.currentTimeMillis();
                Log.e("线程1", String.valueOf(end-begin)+"ms");
            }
        });
        t.start();
    }

}
