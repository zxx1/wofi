package com.wofi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wofi.R;
import com.wofi.constants.Constants;
import com.wofi.utils.Journey;
import com.wofi.utils.Rechargebill;
import com.wofi.utils.TimeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wofi.application.MyApplication.journeyList;

public class JourneyActivity extends AppCompatActivity {
    private ListView lv1;

    private Toolbar journeytoolbar;
    private boolean a=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        journeytoolbar= (Toolbar) findViewById(R.id.xingcheng);
        setSupportActionBar(journeytoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv1= (ListView) findViewById(R.id.xincheng_list);
        SimpleAdapter adapter=new SimpleAdapter(this,getData(), R.layout.journey_item,new String[]{"borrowStartTime","borrowEndTime","cost"},new int[]{R.id.kaishishijian, R.id.jieshushijian,R.id.xiaofei});
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(JourneyActivity.this,MytripActivity.class);
                startActivity(intent1);
            }
        });




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
        for(Journey journey:journeyList){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("borrowStartTime",TimeUtils.getFormatDate(journey.getBorrowStartTime()));
            map.put("borrowEndTime", TimeUtils.getFormatDate(journey.getBorrowEndTime()));
            map.put("cost",journey.getCost());
            list1.add(map);
        }
        return list1;
    }
}
