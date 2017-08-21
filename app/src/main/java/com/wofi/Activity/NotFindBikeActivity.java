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

public class NotFindBikeActivity extends AppCompatActivity{
    private Toolbar toolbar_not_find_bike;
    private TextView find;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_find_bike_activity);
        toolbar_not_find_bike= (Toolbar) findViewById(R.id.toolbar_not_find_bike);
        setSupportActionBar(toolbar_not_find_bike);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        find = (TextView) findViewById(R.id.find);


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
