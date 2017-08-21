package com.wofi.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.wofi.Lock.Unlock;
import com.wofi.R;
import com.wofi.utils.BicycleXY;
import com.wofi.utils.Interaction;

import static com.wofi.application.MyApplication.bicycleId;
import static com.wofi.application.MyApplication.bicycleXYlist;
import static com.wofi.application.MyApplication.startTime;

public class DialogActivity extends Activity {

    private PayPwdEditText payPwdEditText;
    private Button btn_clear = null;
    private Button btn_sure = null;

    private boolean isfull = false;//判断输入编号完整性
    private int isUse = 0;//判断使用状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dialog);
        AlertDialog dialog=new AlertDialog.Builder(DialogActivity.this).create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        payPwdEditText = (PayPwdEditText) findViewById(R.id.ppe_pwd);
        btn_clear = (Button)findViewById(R.id.clear);
        btn_sure = (Button)findViewById(R.id.sure);
        payPwdEditText.initStyle(R.drawable.edit_num_bg, 6, 0.33f,R.color.black_1,R.color.black_1, 20);
        payPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener(){
            @Override
            public void onFinish(String str) {
                MainActivity.setNumber(str);
                isfull = true;
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
                String username=sp.getString("Username","").trim();
                Log.e("Username",username);
                //状态
                if (!isfull){
                    new AlertDialog.Builder(DialogActivity.this).setTitle("提示")
                            .setMessage("请输入完整编号")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();
                                }
                            }).create().show();
                } else if(bicycleXYlist.size()!=0) {
                    for (BicycleXY bicycleXY:bicycleXYlist) {
                        if (bicycleXY.getBicycleId().equals(String.valueOf(Integer.parseInt(MainActivity.getNumber())))) {
                            if (bicycleXY.getBicycleStatement()==-1) {
                                isUse = 1;
                                new android.app.AlertDialog.Builder(DialogActivity.this).setTitle("该车编号：" + MainActivity.getNumber())
                                        .setMessage("该车可能已损坏，请寻找其他可用车！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //finish();
                                            }
                                        }).create().show();
                            } else if (bicycleXY.getBicycleStatement()==0) {
                                isUse = 1;
                                new android.app.AlertDialog.Builder(DialogActivity.this).setTitle("该车编号：" + MainActivity.getNumber())
                                        .setMessage("该车正在使用中，请寻找其他可用车！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //finish();
                                            }
                                        }).create().show();
                            } else {
                                isUse = 1;
                                Interaction.borrowBicycle(String.valueOf(Integer.parseInt(MainActivity.getNumber())),username);
                                startTime=System.currentTimeMillis();
                                bicycleId=bicycleXY.getBicycleId();
                                Log.e("startTime", String.valueOf(startTime));
                                //Toast.makeText(DialogActivity.this,"借车成功,如果没有开锁，请使用蓝牙解锁！",Toast.LENGTH_LONG).show();
                                new android.app.AlertDialog.Builder(DialogActivity.this).setTitle("借车编号：" + MainActivity.getNumber())
                                        .setMessage("如果自动开锁失败，请使用蓝牙开锁！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).create().show();
                                Intent inser = new Intent(DialogActivity.this,Unlock.class);
                                Intent int_over = new Intent();
                                int_over.putExtra("over_fin",1);
                                int_over.setAction("com.wofi.Activity.DialogActivity");
                                sendBroadcast(int_over);
                                startService(inser);
                                //finish();
                            }
                            break;
                        }
                    }
                    if (isUse == 0){
                        new AlertDialog.Builder(DialogActivity.this).setTitle("提示")
                                .setMessage("该车不在附近区域,请重新输入")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //finish();
                                    }
                                }).create().show();
                    }
                } else {
                    new AlertDialog.Builder(DialogActivity.this).setTitle("提示")
                            .setMessage("该车不在附近区域,请重新输入")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();
                                }
                            }).create().show();
                }
            }
        });
    }
}
