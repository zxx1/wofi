package com.wofi.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wofi.constants.Constants;
import com.wofi.utils.Interaction;
import com.wofi.utils.Journey;
import com.wofi.utils.Rechargebill;
import com.wofi.utils.UserInfo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wofi.application.MyApplication.journeyList;
import static com.wofi.application.MyApplication.rechargebillList;
import static com.wofi.application.MyApplication.responceData;
import static com.wofi.application.MyApplication.responceData1;
import static com.wofi.application.MyApplication.responceData2;
import static com.wofi.application.MyApplication.cash;
import static com.wofi.application.MyApplication.userinfo;
import static com.wofi.application.MyApplication.flag;


/**
 * Created by shaohao on 2017/8/4.
 */

public class GsonService extends Service {
    private Timer timer=new Timer();
    private final IBinder binder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flag=false;
        flags = START_STICKY;
        long begin=System.currentTimeMillis();
        Loop();
        Log.e("Service测试","服务启动");
        long end=System.currentTimeMillis();
        Log.e("服务时间", String.valueOf(end-begin)+"ms");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag=true;
        Log.e("Service测试","服务停止");
        timer.cancel();

    }



    /**
     * 使用onbind()方式
     */
    public class MyBinder extends Binder{
        public GsonService getService()
        {
            return GsonService.this;
        }
    }
    private void Loop()
    {

        timer.schedule(new MyTask(),1000,2000);
    }


    class MyTask extends TimerTask{
        @Override
        public void run(){
            // TODO Auto-generated method stub
            SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
            String username=sp.getString("Username","").trim();
            Interaction.queryRecharge(username);
            Interaction.userInfo(username);
            Interaction.getUserCash(username);
        }
    }
}
