package com.example.administrator.mycommonlibrarydemo.fastble;

import android.bluetooth.BluetoothGatt;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import java.util.List;

/**
 * Created by wuyufeng    on  2018/10/31 0031.
 * 扫描各个事件的监听
 */

public interface ScanEventListener {
    
    public void onScanStarted(boolean success);//开始扫描监听
    public void onScanning(BleDevice bleDevice);//扫描中监听
    public void onScanFinished(List<BleDevice> scanResultList);//扫描结束监听
    
    public void onStartConnect();
    public void onConnectFail(BleDevice bleDevice, BleException exception);
    public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status);
    public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice,
        BluetoothGatt gatt, int status);
}
