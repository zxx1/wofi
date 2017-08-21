package com.wofi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.wofi.R;

public class UserGuideActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar userguide;
    private RelativeLayout sysm;
    private RelativeLayout yjsm;
    private RelativeLayout kbls;
    private RelativeLayout zbdc;
    private RelativeLayout czsm;
    private RelativeLayout jbwt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userguide);
        userguide= (Toolbar) findViewById(R.id.userguide);
        setSupportActionBar(userguide);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sysm= (RelativeLayout) findViewById(R.id.sysm);
        zbdc= (RelativeLayout) findViewById(R.id.zbdc);
        yjsm= (RelativeLayout) findViewById(R.id.yjsm);
        czsm= (RelativeLayout) findViewById(R.id.czsm);
        kbls= (RelativeLayout) findViewById(R.id.kbls);
        jbwt= (RelativeLayout) findViewById(R.id.jbwt);
        sysm.setOnClickListener(this);
        yjsm.setOnClickListener(this);
        zbdc.setOnClickListener(this);
        czsm.setOnClickListener(this);
        kbls.setOnClickListener(this);
        jbwt.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.sysm:
                Intent in1=new Intent(UserGuideActivity.this,UseDirection.class);
                startActivity(in1);
                break;
            case R.id.yjsm:
                Intent in2=new Intent(UserGuideActivity.this,CashPledgeActivity.class);
                startActivity(in2);
                break;
            case R.id.kbls:
                Intent in3=new Intent(UserGuideActivity.this,NotLockActivity.class);
                startActivity(in3);
                break;
            case R.id.czsm:
                Intent in4=new Intent(UserGuideActivity.this,RechangeInstructionsActivity.class);
                startActivity(in4);
                break;
            case R.id.jbwt:
                Intent in5=new Intent(UserGuideActivity.this,IllegalParkingActivity.class);
                startActivity(in5);
                break;
            case R.id.zbdc:
                Intent in6=new Intent(UserGuideActivity.this,NotFindBikeActivity.class);
                startActivity(in6);
                break;
        }

    }
}
