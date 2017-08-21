package com.wofi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wofi.R;
import com.wofi.utils.Rechargebill;
import com.wofi.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wofi.application.MyApplication.rechargebillList;


public class RechargebillActivity extends AppCompatActivity {
    private ListView lv;
    private Toolbar rechargebilltoolbar;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechargebill);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        rechargebilltoolbar= (Toolbar) findViewById(R.id.rechargebill);
        setSupportActionBar(rechargebilltoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                init();
                            }
                        });
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
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
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for(Rechargebill rechargebill:rechargebillList){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("rechargeAmount",rechargebill.getRechargeAmount());
            map.put("rechargeTime", TimeUtils.getFormatDate(rechargebill.getRechargeTime()));
            list.add(map);
        }
        return list;
    }
    public void init()
    {
        lv= (ListView) findViewById(R.id.id_listview);
        SimpleAdapter adapter=new SimpleAdapter(this,getData(), R.layout.rechargebill_item,new String[]{"rechargeAmount","rechargeTime"},new int[]{R.id.rechargeAmount, R.id.rechargeTime});
        lv.setAdapter(adapter);
    }


}
