package com.wofi.Lock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wofi.Activity.DialogActivity;
import com.wofi.Activity.MainActivity;
import com.wofi.R;
import com.wofi.utils.Interaction;

import java.text.DecimalFormat;

import static com.wofi.R.id.tv1;
import static com.wofi.application.MyApplication.bicycleId;
import static com.wofi.application.MyApplication.endTime;

public class Lockbike extends Activity {

    private double kcal = 0;
    private int temp = 1;
    private int my_data;

    private TextView mynumber = null;
    private TextView mymoney = null;
    private TextView time_tv = null;
    private TextView fire_tv = null;
    private Button back_bike = null;
    private Button over_bike = null;

    private MyReceiver receiver=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockbike);
        AlertDialog dialog=new AlertDialog.Builder(Lockbike.this).create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.TOP);
        mynumber = (TextView)findViewById(tv1);
        mymoney = (TextView)findViewById(R.id.my_money);
        time_tv = (TextView)findViewById(R.id.time1);
        fire_tv = (TextView)findViewById(R.id.fire1);
        back_bike = (Button)findViewById(R.id.back_bike);
        over_bike = (Button)findViewById(R.id.over);
        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.wofi.Lock.Unlock");
        Lockbike.this.registerReceiver(receiver,filter);
        mynumber.setText("用车编号：" + MainActivity.getNumber());
        back_bike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        over_bike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                endTime=System.currentTimeMillis();
                Log.e("endTime", String.valueOf(endTime));
                SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
                String username=sp.getString("Username","").trim();
                Intent overservice = new Intent(Lockbike.this,Unlock.class);
                stopService(overservice);
                Interaction.returnBicycle(bicycleId,username,MainActivity.getEndT_lon(),MainActivity.getEndT_lat(), String.valueOf(my_data));
                new AlertDialog.Builder(Lockbike.this).setTitle("还车成功")
                        .setMessage("谢谢使用，请记得把车锁好")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
            }
        });
    }

    /**
     * 获取广播数据
     * @author jiqinlin
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int count = bundle.getInt("count");
            if (count > temp) {
                time_tv.setText(count / 60 + "分" + count % 60 + "秒");
                my_data = count/3600 + 1;
                mymoney.setText("消费：" + my_data + "元");
                kcal = 7 * 50 * 1.05 / 3600 * count;
                DecimalFormat df = new DecimalFormat("#.###");
                double kcal1 = Double.parseDouble(df.format(kcal));
                fire_tv.setText(kcal1 + "卡路里");
            }else{}
        }
    }
}
