package com.wofi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wofi.R;
import com.wofi.Service.GsonService;
import com.wofi.constants.Constants;
import com.wofi.utils.HttpUtils;
import com.wofi.utils.Interaction;

import java.io.IOException;


public class ReturnCashActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text_return;
    private Button btn_return;
    private Toolbar t_return;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_cash);
        text_return = (TextView) findViewById(R.id.text_return);
        t_return = (Toolbar) findViewById(R.id.t_return);
        btn_return= (Button) findViewById(R.id.btn_return);
        setSupportActionBar(t_return);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        text_return.setOnClickListener(this);
        btn_return.setOnClickListener(this);
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
        switch (view.getId()) {
            case R.id.btn_return:
                UserCashActivity.setPill(0);
                SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                Interaction.returnUserCash(sp.getString("Username",""));
                Intent in1 =new Intent(ReturnCashActivity.this,MyWalletActivity.class);
                startActivity(in1);
                Toast.makeText(this,"退款成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }


    }
}
