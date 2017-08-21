package com.wofi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wofi.R;
import com.wofi.Service.GsonService;

import static com.wofi.application.MyApplication.userinfo;


public class MyWalletActivity1 extends AppCompatActivity implements View.OnClickListener{
    private Toolbar wallet;
    private Button chongzhi,chongyajin,mingxi;
    private TextView yue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet1);
        wallet= (Toolbar) findViewById(R.id.wallet1);
        setSupportActionBar(wallet);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chongzhi= (Button) findViewById(R.id.chongzhi1);
        chongyajin= (Button) findViewById(R.id.chongyajin1);
        mingxi= (Button) findViewById(R.id.mingxi1);
        yue= (TextView) findViewById(R.id.chefei1);
        chongzhi.setOnClickListener(this);
        chongyajin.setOnClickListener(this);
        mingxi.setOnClickListener(this);
        yue.setText(String.valueOf(userinfo.getUserAccount()));

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
            case R.id.chongzhi1:
                Intent in1=new Intent(MyWalletActivity1.this,RechargeActivity.class);
                startActivity(in1);
                break;
            case R.id.chongyajin1:
                Intent in2=new Intent(MyWalletActivity1.this,ReturnCashActivity.class);
                startActivity(in2);
                finish();
                break;
            case R.id.mingxi1:
                Intent in3=new Intent(MyWalletActivity1.this,RechargebillActivity.class);
                startActivity(in3);
                break;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        yue.setText(String.valueOf(userinfo.getUserAccount()));
        Log.e("余额", String.valueOf(userinfo.getUserAccount()));
    }

}
