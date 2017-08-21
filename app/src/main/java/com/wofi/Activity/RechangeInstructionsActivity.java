package com.wofi.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.widget.TextView;

import com.wofi.R;

/**
 * Created by zxx on 2017/8/8.
 */

public class RechangeInstructionsActivity  extends AppCompatActivity{
    private Toolbar toolbar_rechange_in;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rechange_instructions_activity);
        toolbar_rechange_in= (Toolbar) findViewById(R.id.toolbar_rechange_in);
        setSupportActionBar(toolbar_rechange_in);
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
