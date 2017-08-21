package com.wofi.Bluetooth;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wofi.R;

import java.util.ArrayList;

public class MySpinnerAdapter extends BaseAdapter {
    private ArrayList<BlueToothDeviceBean> mDatas;
    private LayoutInflater mInflater;

    public MySpinnerAdapter(Context context, ArrayList<BlueToothDeviceBean> datas) {
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mHolder = null;
        //获取设备
        BlueToothDeviceBean item = mDatas.get(i);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, viewGroup, false);
            mHolder = new ViewHolder((View) convertView.findViewById(R.id.list_child), (TextView) convertView.findViewById(R.id.chat_msg));
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        //是否连接过
        if (item.isReceive) {
            //设置背景颜色 以区分是否连接过
            Log.e("wofi","设备连接过");
            mHolder.child.setBackgroundResource(R.color.colorWhite);
        } else {
            Log.e("wofi","里面的设备未连接");
            mHolder.child.setBackgroundResource(R.color.colorBlank);
        }
        mHolder.msg.setText(item.message);//设备名字
        return convertView;
    }

    //判断缓存
    // 一个临时的储存器
    // 把你getView方法中每次返回的View存起来，可以下次再用。
    // 这样做的好处就是不必每次都到布局文件中去拿到你的View，
    // 提高了效率。
    //正确的加载方式是当convertView不为空的时候直接重新使用
    //convertView从而减少了很多不必要的View的创建，然后加载数据
    // 最快的方式是定义一个ViewHolder，
    // 将convetView的tag设置为ViewHolder,不为空时重新使用即可

    class ViewHolder {
        protected View child;
        protected TextView msg;

        public ViewHolder(View child, TextView msg) {
            this.child = child;
            this.msg = msg;
        }
    }

}
