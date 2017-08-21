package com.wofi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.wofi.R;


public class RechargeSucessActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent=getIntent();
        String whetherpay=intent.getStringExtra("ispay");
        Log.e("sssssssss",whetherpay);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechargesucess);
        toolbar= (Toolbar) findViewById(R.id.sucess);
        textView=(TextView)findViewById(R.id.textView6);
        if(whetherpay.equals("0")){
            textView.setText("微信"+"充值成功");}
        else{
            textView.setText("支付宝"+"充值成功");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}
