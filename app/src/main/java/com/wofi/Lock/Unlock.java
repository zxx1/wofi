package com.wofi.Lock;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wofi.R;

import static com.wofi.R.id.float_id;

public class Unlock extends Service {

    private int recLen = 0;
    private boolean ts = false;
    private boolean fir = true;
    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;
    Button mFloatView;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        createFloatView();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams();
        //获取WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 150;

        // 设置悬浮窗口长宽数据
        // wmParams.width = 200;
        // wmParams.height = 80;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.unlockft_layout, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        //浮动窗口按钮
        mFloatView = (Button) mFloatLayout.findViewById(float_id);
        handler.postDelayed(runnable, 1000);
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //设置监听浮动窗口的触摸移动
        mFloatView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth() / 2;
                //25为状态栏的高度
                wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight() / 2 - 25;
                //刷新
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return false;
            }
        });

        mFloatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                fir = false;
                Intent intent = new Intent(Unlock.this, Lockbike.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen++;
            mFloatView.setText(recLen/60 + "分" + recLen%60 + "秒");
            //发送广播
            Intent intent = new Intent();
            intent.putExtra("count",recLen);
            intent.setAction("com.wofi.Lock.Unlock");
            sendBroadcast(intent);
            if(ts){
                Message message = new Message();
                message.what = 1;
                handlerStop.sendMessage(message);
            }
            handler.postDelayed(this, 1000);
        }
    };

    final Handler handlerStop = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    recLen = 0;
                    handler.removeCallbacks(runnable);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ts=true;
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }
    }
}
