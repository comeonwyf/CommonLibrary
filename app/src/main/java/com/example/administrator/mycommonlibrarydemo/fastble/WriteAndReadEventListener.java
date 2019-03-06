package com.example.administrator.mycommonlibrarydemo.fastble;

import android.bluetooth.BluetoothGatt;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

/**
 * Created by wuyufeng    on  2018/10/31 0031.
 * 读写的监听
 */

public interface WriteAndReadEventListener {
    
    public void onStartConnect();
    public void onConnectFail(BleDevice bleDevice, BleException exception);
    public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status);
    public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice,
        BluetoothGatt gatt, int status);
    
    public void onWriteSuccess(int current, int total, byte[] justWrite);
    public void onWriteFailure(BleException exception);
    public void onNotifySuccess();
    public void onNotifyFailure(BleException exception);
    public void onCharacteristicChanged(byte[] data);
    
}
