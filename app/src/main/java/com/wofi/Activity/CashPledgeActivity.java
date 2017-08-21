package com.wofi.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wofi.R;
import com.wofi.utils.PayPwdEditText;

/**
 * Created by zxx on 2017/8/8.
 */

public class CashPledgeActivity extends AppCompatActivity {
    private Toolbar toolbar_cash_pledge;
    private TextView cash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_pledge_activity);
        toolbar_cash_pledge= (Toolbar) findViewById(R.id.toolbar_cash_pledge);
        setSupportActionBar(toolbar_cash_pledge);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cash = (TextView) findViewById(R.id.cash);


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
