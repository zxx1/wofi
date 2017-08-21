package com.wofi.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.iflytek.cloud.thirdparty.B;
import com.wofi.R;

public class WhereCash extends Activity {

    Button btn_over_cash = null;
    Button btn_cash = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_cash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AlertDialog dialog=new AlertDialog.Builder(WhereCash.this).create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.TOP);
        btn_over_cash = (Button)findViewById(R.id.clear_cash);
        btn_cash = (Button)findViewById(R.id.cash_sure);
        btn_over_cash.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_cash.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent itn = new Intent(WhereCash.this,UserCashActivity.class);
                startActivity(itn);
                finish();
            }
        });
    }
}
