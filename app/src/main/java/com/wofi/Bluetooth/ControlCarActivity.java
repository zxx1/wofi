package com.wofi.Bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.wofi.Activity.MainActivity;
import com.wofi.Lock.Unlock;
import com.wofi.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ControlCarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int STATUS_CONNECTING = 1;//正在连接
    private static final int STATUS_ERROR = 2;//连接失败
    private static final int STATUS_CONNECT = 3;//连接成功
    private static final int STATUS_BACK = 4;//返回消息
    private String serverAdress;//选择设备地址
    //行动指令
    private final String GO = "解锁";
    private final String lock = "上锁";

    //判断小车端是否断开
    private boolean check;

    private Spinner spinner_car_control;
    private LinearLayout ll_bluetooth;
    private Button btn_Control_search;
    private Button btn_Control_go;
    private Button btn_Control_closeClient;
    private Button btn_lock;
    //蓝牙
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    public static final int REQUEST_OPEN = 0x01;//打开一个蓝牙
    private MySpinnerAdapter mySpinnerAdapter;//Spinner适配器
    private ArrayList<BlueToothDeviceBean> mDatas;//数据
    //线程类
    private ClientThread mClientThread;
    private ReadThread mReadThread;
    // 蓝牙客户端socket
    private BluetoothSocket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_car);
        registerBroadcast();//注册广播
        initDatas();//初始化数据
        initUI();//初始化布局
    }

    //注册广播
    private void registerBroadcast() {
        //设备被发现广播  蓝牙扫描时，扫描到任一远程蓝牙设备时，会发送此广播。给onreceive
        IntentFilter discoveryFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, discoveryFilter);

        // 设备发现完成  蓝牙扫描过程结束
        IntentFilter foundFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, foundFilter);
    }

    //发现广播
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 获得设备信息
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 如果绑定的状态不一样(判断给定地址下的device是否已经配对)
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mDatas.add(new BlueToothDeviceBean(device.getName() + "\n" + device.getAddress(), false));
                    mySpinnerAdapter.notifyDataSetChanged();
                }
                // 如果搜索完成了
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                if (spinner_car_control.getCount() == 0) {
                    mDatas.add(new BlueToothDeviceBean("没有发现蓝牙设备", false));
                    mySpinnerAdapter.notifyDataSetChanged();
                }
                mySpinnerAdapter.notifyDataSetChanged();
                btn_Control_search.setText("重新搜索");
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_Control_search:
                //是否正在处于扫描过程中。
                if (mBluetoothAdapter.isDiscovering()) {

                    mBluetoothAdapter.cancelDiscovery();
                    btn_Control_search.setText("重新搜索");

                } else {
                    mDatas.clear();
                    mySpinnerAdapter.notifyDataSetChanged();
                    //进行加载设备初始化数据
                    init();
                /* 开始搜索 */
                    mBluetoothAdapter.startDiscovery();
                    btn_Control_search.setText("ֹͣ停止搜索");
                }
                break;
            case R.id.btn_Control_go:
                sendMessage(GO);//发送指令
                finish();
                Intent intentmain = new Intent(ControlCarActivity.this, MainActivity.class);
                startActivity(intentmain);
                Intent intentservers = new Intent(ControlCarActivity.this,Unlock.class);
                startService(intentservers);
                break;
            case R.id.lock:
                sendMessage(lock);//发送指令
                break;
            case R.id.btn_Control_closeClient:
                //关闭控制端
                shutdownClient();
                Intent intent = new Intent(this,ControlCarActivity.class);
                startActivity(intent);
                showToast("控制端断开连接");
                finish();
                break;


        }
    }

    //蓝牙发送消息
    private void sendMessage(String data) {
        //String msg = String.valueOf(data);
        if (mSocket == null) {
            showToast("没有连接");
            return;
        }
        try {
            showToast("发出的指令是" + data);
            //输出流输出信息
            OutputStream os = mSocket.getOutputStream();
            os.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //条目点击
    private class SpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int i,
                                   long arg3) {
            Log.e("gaoyu", "onItemSelected");
            BlueToothDeviceBean bean = mDatas.get(i);
            String info = bean.message;
            String address = info.substring(info.length() - 17);

            serverAdress = address;

            AlertDialog.Builder stopDialog = new AlertDialog.Builder(ControlCarActivity.this);
            stopDialog.setTitle("连接");//标题
            stopDialog.setMessage(bean.message);
            stopDialog.setPositiveButton("连接", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mBluetoothAdapter.cancelDiscovery();
                    btn_Control_search.setText("重新搜索");
                    //进行连接
                    if (!"".equals(serverAdress)) {
                        mDevice = mBluetoothAdapter.getRemoteDevice(serverAdress);
                        //打开客户端线程
                        mClientThread = new ClientThread();
                        mClientThread.start();
                    } else {
                        showToast("地址为空");
                    }
                    dialog.cancel();
                }
            });
            stopDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            stopDialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    //客户端线程
    private class ClientThread extends Thread {
        public void run() {
            try {
                mSocket = mDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

                Message msg = new Message();
                msg.obj = "请稍候，正在连接服务器:" + serverAdress;
                msg.what = STATUS_CONNECTING;
                mHandler.sendMessage(msg);

                mSocket.connect();

                msg = new Message();
                msg.obj = "已经连接上服务端！可以发送信息。";
                msg.what = STATUS_CONNECT;
                mHandler.sendMessage(msg);
                //TODO 启动接受数据  服务端返回数据(暂时用途不大)
                mReadThread = new ReadThread();
                mReadThread.start();

                //注意子线程不能刷新
                //UIViewRootImpl$CalledFromWrongThreadException:
                //ll_bluetooth.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                Message msg = new Message();
                msg.obj = "连接服务端异常！";
                msg.what = STATUS_ERROR;
                mHandler.sendMessage(msg);
                showToast("失去连接");
            }
        }
    }

    //数据初始化
    private void initDatas() {
        mDatas = new ArrayList<BlueToothDeviceBean>();
        mySpinnerAdapter = new MySpinnerAdapter(this, mDatas);
        //实例化蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //判断蓝牙功能呢是否存在
        if (mBluetoothAdapter == null) {
            showToast("无蓝牙模块");
            return;
        }
    }

    //停止客户端连接
    private void shutdownClient() {
        new Thread() {
            public void run() {
                if (mClientThread != null) {
                    mClientThread.interrupt();
                    mClientThread = null;
                }
                if (mReadThread != null) {
                    mReadThread.interrupt();
                    mReadThread = null;
                }
                if (mSocket != null) {
                    try {
                        mSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mSocket = null;
                }
            }
        }.start();
    }


    //信息处理
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String info = (String) msg.obj;
            switch (msg.what) {
                case STATUS_CONNECTING:
                    showToast(info);//吐司出 连接状态
                    break;
                case STATUS_CONNECT:
                    ll_bluetooth.setVisibility(View.VISIBLE);//显示隐藏的界面
                    showToast(info);
                    break;
                case STATUS_ERROR:
                    showToast(info);
                    break;
                case STATUS_BACK:
                    showToast(info);
                    break;
            }
        }
    };

    // 从服务端返回值
    private class ReadThread extends Thread {
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream is = null;
            try {
                is = mSocket.getInputStream();
                while (true) {
                    if ((bytes = is.read(buffer)) > 0) {
                        byte[] buf_data = new byte[bytes];
                        for (int i = 0; i < bytes; i++) {
                            buf_data[i] = buffer[i];
                        }
                        String s = new String(buf_data);
                        Message msg = new Message();
                        msg.obj = "服务端返回值" + s;
                        msg.what = STATUS_BACK;
                        mHandler.sendMessage(msg);
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    //列出所有的蓝牙设备
    private void init() {
        //根据适配器得到所有的设备信息
        Set<BluetoothDevice> deviceSet = mBluetoothAdapter.getBondedDevices();
        Log.e("gaoyu", "里面的设备个数" + deviceSet.size());
        if (deviceSet.size() > 0) {
            for (BluetoothDevice device : deviceSet) {
                mDatas.add(new BlueToothDeviceBean(device.getName() + "\n" + device.getAddress(), true));
                mySpinnerAdapter.notifyDataSetChanged();//动态刷新
            }
        } else {
            mDatas.add(new BlueToothDeviceBean("没有配对的设备", true));
            mySpinnerAdapter.notifyDataSetChanged();
        }
    }

    //吐司设置
    public void showToast(String s) {
        Toast toast = Toast.makeText(ControlCarActivity.this, s, Toast.LENGTH_SHORT);//设置吐司显示位置
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);//设置偏移量
        toast.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        //判断蓝牙是否打开
        if (mBluetoothAdapter.isEnabled()) {
            showToast("蓝牙已经打开了");
        } else {
            //强制打开蓝牙
            /*boolean isOpen = mBluetoothAdapter.enable();
              showToast("" + isOpen);*/
            //调用action打开（start Activity for result）
            Intent open = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(open, REQUEST_OPEN);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(mReceiver);
        shutdownClient();//关闭控制端
    }

    //初化UI
    private void initUI() {
        spinner_car_control = (Spinner) findViewById(R.id.Spinner_car_Control);
        ll_bluetooth = (LinearLayout) findViewById(R.id.ll_bluetooth);
        btn_Control_search = (Button) findViewById(R.id.btn_Control_search);
        btn_Control_go = (Button) findViewById(R.id.btn_Control_go);
        btn_lock = (Button)findViewById(R.id.lock);
        btn_Control_closeClient = (Button) findViewById(R.id.btn_Control_closeClient);

        btn_Control_search.setOnClickListener(this);
        btn_Control_go.setOnClickListener(ControlCarActivity.this);
        btn_Control_closeClient.setOnClickListener(this);
        btn_lock.setOnClickListener(this);

        ll_bluetooth.setVisibility(View.INVISIBLE);//隐藏控制布局
        spinner_car_control.setAdapter(mySpinnerAdapter);//spinner配置
        spinner_car_control.setPrompt("请选择相应设备");//加标题
        spinner_car_control.setOnItemSelectedListener(new SpinnerOnSelectedListener());//条目点击
    }
}
