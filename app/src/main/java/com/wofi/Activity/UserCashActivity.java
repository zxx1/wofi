package com.wofi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wofi.R;
import com.wofi.Service.GsonService;
import com.wofi.constants.Constants;
import com.wofi.utils.HttpUtils;
import com.wofi.utils.Interaction;

import java.io.IOException;


public class UserCashActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView Pay;
    private Toolbar credit;
    private RadioGroup radioGroup;
    private int ispay;
    private  static int pill = 0;//判断是否充值押金
    public static int getPill(){
        return pill;
    }
    public static void setPill(int pill){
        UserCashActivity.pill = pill;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        Pay = (TextView) findViewById(R.id.Pay);
        credit = (Toolbar) findViewById(R.id.credit);
        setSupportActionBar(credit);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Pay.setOnClickListener(this);
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
            case R.id.Pay:
                UserCashActivity.setPill(1);
                SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                Interaction.submitUserCash(sp.getString("Username",""));
                Toast.makeText(this,"充值成功",Toast.LENGTH_SHORT).show();
                Intent in=new Intent(UserCashActivity.this,MyWalletActivity1.class);
                startActivity(in);
                finish();
                break;
        }
    }

}
