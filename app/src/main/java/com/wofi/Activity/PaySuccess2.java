package com.wofi.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.cloud.thirdparty.B;
import com.wofi.R;

public class PaySuccess2 extends AppCompatActivity {

    ImageView icon = null;
    TextView zhifu = null;
    TextView pay_mon = null;
    Context ctx = null;
    Button finish_btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success2);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        ctx = getBaseContext();
        icon = (ImageView)findViewById(R.id.icon_fi);
        zhifu = (TextView)findViewById(R.id.whether);
        pay_mon = (TextView)findViewById(R.id.pay_money);
        finish_btn = (Button)findViewById(R.id.fini_btn);
        finish_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        String whetherpay=intent.getStringExtra("ispay");
        String mymoney = intent.getStringExtra("mypay");
        if(whetherpay.equals("0")){
            zhifu.setText("微信支付");
            //icon.setImageResource(getResources().getIdentifier("wxweixin","drawable",ctx.getPackageName()));
        }
        else{
            zhifu.setText("支付宝支付");
            //icon.setImageResource(getResources().getIdentifier("zhifubao","drawable",ctx.getPackageName()));
        }
        pay_mon.setText("￥" + mymoney + ".00");
    }
}
