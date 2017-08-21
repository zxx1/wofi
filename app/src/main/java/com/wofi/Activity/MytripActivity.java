package com.wofi.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.wofi.R;

import java.util.ArrayList;
import java.util.List;

public class MytripActivity extends AppCompatActivity implements AMapLocationListener {

    private ImageView ivbtn1 = null;
    private ImageView ivbtn2 = null;
    private MapView mMapView;
    private AMap aMap;
    public AMapLocationClient client;
    public AMapLocationClientOption aMapLocationClientOption;
    private List<LatLng> list;
    Polyline polyline;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mytrip);
        super.onCreate(savedInstanceState);
        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){actionBar.hide();}*/
        mMapView = (MapView) findViewById(R.id.my_trip);
        aMap=mMapView.getMap();
        list=new ArrayList<>();

        ivbtn1 = (ImageView)findViewById(R.id.imagebtn1);
        ivbtn2 = (ImageView)findViewById(R.id.imagebtn2);
        ivbtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivbtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "这是我的行程");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));

            }
        });
        LatLng []latLng={new LatLng(32.0361120000,118.6422530000), new LatLng(32.0363950000,118.6417760000),new LatLng(32.0367550000,118.6409720000),
                new LatLng(32.0374870000,118.6396470000), new LatLng(32.0388220000,118.6407950000),new LatLng(32.0381990000,118.6419970000),
                new LatLng(32.0386450000,118.6423730000), new LatLng(32.0393880000,118.6429900000),new LatLng(32.0389260000,118.6438480000),
                new LatLng(32.0382670000,118.6449910000), new LatLng(32.0374260000,118.6443150000),new LatLng(32.0357520000,118.6427380000)
        };

        for(int i=0;i<latLng.length;i++)
        {
            list.add(latLng[i]);
        }
        client = new AMapLocationClient(getApplicationContext());
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        aMapLocationClientOption = new AMapLocationClientOption(); // 声明定位参数
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        aMapLocationClientOption.setNeedAddress(true); //设置是否返回地址信息（默认返回地址信息）
        aMapLocationClientOption.setOnceLocation(false);
        //设置是否只定位一次,默认为false
        aMapLocationClientOption.setWifiActiveScan(true); //设置是否强制刷新WIFI，默认为强制刷新
        aMapLocationClientOption.setMockEnable(true); //设置是否允许模拟位置,默认为false，不允许模拟位置
        aMapLocationClientOption.setInterval(200000); //设置定位间隔,单位毫秒,默认为2000ms
        client.setLocationOption(aMapLocationClientOption); //给定位客户端对象设置定位参数
        client.startLocation(); // 开始定位
        client.setLocationListener(this);

    }  @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //Toast.makeText(MytripActivity.this, "正在定位", Toast.LENGTH_SHORT).show(); // 检测一下你当前是否正在定位
        LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()); // 获取你当前的位置信息
        if (aMapLocation.getLatitude() == 0.0 && aMapLocation.getLongitude() == 0.0)
        { Toast.makeText(MytripActivity.this, "请检查网络", Toast.LENGTH_SHORT).show(); // 当网络有问题或者刚开始定位的时候会出现经纬度为0的情况，这里进行过滤
        }
        else { list.add(0, latLng); }
        polyline = aMap.addPolyline(new PolylineOptions().color(Color.GREEN)); polyline.setPoints(list); //
        // 绘制轨迹
        if (marker != null)
        { marker.remove(); }
        marker = aMap.addMarker(new MarkerOptions().position(list.get(0)) .anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromResource(R.drawable.greenbike)));
        // 在你的当前位置绘制一个红点，标出你所在的位置，并且会随着你位置的移动而移动
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(list.get(0), 15); aMap.animateCamera(update); // 以你当前的位置为中心，并且地图大小级别为16.3 }
    }
}

