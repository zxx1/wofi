package com.wofi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wofi.R;
import com.wofi.Service.GsonService;
import com.wofi.constants.Constants;
import com.wofi.utils.DemoAdapter;
import com.wofi.utils.HttpUtils;
import com.wofi.utils.Interaction;
import com.wofi.utils.ItemModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;


public class RechargeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private TextView tvPay;
    private TextView tv_recharge_money;
    private String a;
    private RadioGroup radioGroup;
    private int ispay=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        tvPay = (TextView) findViewById(R.id.tvPay);
        tv_recharge_money = (TextView) findViewById(R.id.tv_recharge_money);
        radioGroup=(RadioGroup)findViewById(R.id.Pay1);
        // RecyclerView 的Item宽或者高不会变(提升性能)。
        recyclerView.setHasFixedSize(true);
        //recycleview设置布局方式，GridView (一行三列)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter = new DemoAdapter());
        adapter.replaceAll(getData(),this);
        EventBus.getDefault().register(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i==R.id.weixin)
                { ispay=0;}
                else{ispay=1;

                }
            }
        });
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                Interaction.recharge(a,sp.getString("Username",""));
                if(ispay==0)
                { Intent intent=new Intent(RechargeActivity.this,PaySuccess2.class);
                    String whether_pay= Integer.toString(ispay);
                    intent.putExtra("ispay",whether_pay);
                    intent.putExtra("mypay",a);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(RechargeActivity.this,PaySuccess2.class);
                    String whether_pay= Integer.toString(ispay);
                    intent.putExtra("ispay",whether_pay);
                    intent.putExtra("mypay",a);
                    startActivity(intent);
                }

                finish();
            }

        });

    }

    public ArrayList<ItemModel> getData() {
        String data = "5,10,20,30,50,100";
        // isDiscount ：1、有角标 2、无角标
        String isDiscount = "2";
        String dataArr[] = data.split(",");
        ArrayList<ItemModel> list = new ArrayList<>();
        for (int i = 0; i < dataArr.length; i++) {
            String count = dataArr[i] + "元";
            if (isDiscount.equals("1") && i == 0) {
                list.add(new ItemModel(ItemModel.ONE, count, true));
            } else {
                list.add(new ItemModel(ItemModel.TWO, count, false));
            }
        }

        return list;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAdapterClickInfo(ItemModel model) {
        String money = model.data.toString().replace("元", "");
        tv_recharge_money.setText(money);
        a=money;
        Log.e("充值",money);
        Toast.makeText(this,"确认充值", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

