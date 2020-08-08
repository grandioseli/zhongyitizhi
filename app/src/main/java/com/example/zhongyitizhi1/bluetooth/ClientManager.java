package com.example.zhongyitizhi1.bluetooth;//package com.example.zhongyitizhi1.bluetooth;

import com.inuker.bluetooth.library.BluetoothClient;
import com.zhuoting.health.MyApplication;

public  class ClientManager {
    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(MyApplication.getInstance());
                }
            }
        }
        return mClient;
    }
}
