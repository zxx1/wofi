package com.wofi.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.wofi.R;

public class WalkRouteCalculateActivity extends BaseActivity {

    double st_la;
    double st_lo;
    double en_la;
    double en_lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_navi);

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);

        Intent intent=getIntent();
        String start_lat=intent.getStringExtra("st_lat");
        String start_lon=intent.getStringExtra("st_lon");
        String end_latitude=intent.getStringExtra("end_lat");
        String end_longtitude=intent.getStringExtra("end_lon");

        //Log.i("st_lat",start_lat);
        //Log.i("st_lon",start_lon);
        //Log.i("en_lat",end_latitude);
        //Log.i("en_lon",end_longtitude);
        st_la=Double.parseDouble(start_lat);
        st_lo=Double.parseDouble(start_lon);
        en_la=Double.parseDouble(end_latitude);
        en_lo=Double.parseDouble(end_longtitude);
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        //mAMapNavi.startNavi(NaviType.GPS);
    }
    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        mAMapNavi.calculateWalkRoute(new NaviLatLng(st_la, st_lo), new NaviLatLng(en_la,en_lo));

    }
    @Override
    public void onStartNavi(int i)
    {

    }
    @Override
    public void onCalculateRouteSuccess() {
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
