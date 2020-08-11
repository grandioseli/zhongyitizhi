package com.example.zhongyitizhi1.bluetooth;//package com.example.zhongyitizhi1.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.zhongyitizhi1.R;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.search.SearchResult;

public class BlueDeviceHolder extends RecyclerView.ViewHolder {
    Context mContext;
    private Button mBlueDeviceNameView;
    private SearchResult mBlueDevice;
    private MyBleService mMyBleService = MyBleService.getInstance();
    private static final String BLUE_DEVICE_MAC ="com.example.smartBracelet.blueDeviceMAC";
    public void bind(SearchResult blueDevice){
        mBlueDevice = blueDevice;
        //在这设置mBlueDeviceNameView，mBlueDeviceMacView的内容
        mBlueDeviceNameView.setText(blueDevice.getName());
    }

    public BlueDeviceHolder(LayoutInflater inflater, ViewGroup parent, Context context){
        //实例化ViewHolder
        super(inflater.inflate(R.layout.list_item_bluedevice,parent,false));
        mContext = context;
        mBlueDeviceNameView = itemView.findViewById(R.id.blueDeviceNameView);
        mBlueDeviceNameView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectDeviceIfNeeded();
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        connectDeviceIfNeeded();
//    }
    private void connectDeviceIfNeeded() {
        if (!mMyBleService.isConnected) {
            connectDevice();
        }
    }

    private void connectDevice() {
        mMyBleService.connect(mBlueDevice.getAddress());
    }

}
