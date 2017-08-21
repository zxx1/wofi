package com.wofi.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.wofi.R;

public class UseDirection extends AppCompatActivity {

    private Toolbar toolbar_usedire;
    private TextView use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_direction);
        toolbar_usedire = (Toolbar)findViewById(R.id.usedire);
        use = (TextView)findViewById(R.id.dire);
        setSupportActionBar(toolbar_usedire);
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
