package com.wofi.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wofi.Bluetooth.ControlCarActivity;
import com.wofi.R;

import java.text.DecimalFormat;

public class ScanActivity extends AppCompatActivity {

    private CaptureFragment captureFragment;
    ImageView input = null;
    ImageView light = null;
    ImageView bluth = null;

    int isFinish = 0;
    private MyReceiver receiver=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);

        //执行扫描Fragment的初始化操作
        captureFragment=new CaptureFragment();
        //设置定制化扫描界面
        CodeUtils.setFragmentArgs(captureFragment,R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        //替换我们的扫描控件
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container,captureFragment).commit();
        initView();

        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.wofi.Activity.DialogActivity");
        this.registerReceiver(receiver,filter);

    }
    public static boolean isOpen = false;

    private void initView() {
        input = (ImageView)findViewById(R.id.input1);
        light = (ImageView)findViewById(R.id.light1);
        bluth = (ImageView)findViewById(R.id.bluth1);

        //手动输入
        input.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(ScanActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });

        //手电筒
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (!isOpen) {
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }
            }
        });

        //蓝牙解锁
        bluth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ScanActivity.this, ControlCarActivity.class);
                startActivity(intent);
            }
        });

    }
    CodeUtils.AnalyzeCallback analyzeCallback=new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            ScanActivity.this.setResult(RESULT_OK, resultIntent);
            ScanActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            ScanActivity.this.setResult(RESULT_OK, resultIntent);
            ScanActivity.this.finish();

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取广播数据,结束本页面
     * @author jiqinlin
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            isFinish = bundle.getInt("over_fin");
            if (isFinish == 1){
                ScanActivity.this.finish();
            }
            /*int count = bundle.getInt("count");
            if (count > temp) {
                time_tv.setText(count / 60 + "分" + count % 60 + "秒");
                my_data = count/3600 + 1;
                mymoney.setText("消费：" + my_data + "元");
                kcal = 7 * 50 * 1.05 / 3600 * count;
                DecimalFormat df = new DecimalFormat("#.###");
                double kcal1 = Double.parseDouble(df.format(kcal));
                fire_tv.setText(kcal1 + "卡路里");
            }else{}*/
        }
    }
}
