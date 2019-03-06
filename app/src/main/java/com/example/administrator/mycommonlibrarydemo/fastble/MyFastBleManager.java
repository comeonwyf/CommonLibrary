package com.example.administrator.mycommonlibrarydemo.fastble;

import android.bluetooth.BluetoothGatt;
import android.text.TextUtils;
import android.util.Log;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import java.util.List;

/**
 * Created by wuyufeng    on  2018/10/31 0031.
 * fastBle的管理类
 */

public class MyFastBleManager  {

    private static volatile MyFastBleManager mMyFastBleManager = null;
    private final BleScanRuleConfig.Builder mBuilder;
    private ScanEventListener mListener;
    private WriteAndReadEventListener mWriteAndReadEventListener;

    private MyFastBleManager() {
        //初始设置Ble
        BleManager.getInstance()
            .enableLog(true)
            .setReConnectCount(1, 5000)
            .setConnectOverTime(20000)
            .setOperateTimeout(5000);

        mBuilder = new BleScanRuleConfig.Builder();
    }

    public static MyFastBleManager getInstance() {
        MyFastBleManager myFastBleManager = mMyFastBleManager;
        if (myFastBleManager == null) {
            synchronized (MyFastBleManager.class) {
                myFastBleManager = new MyFastBleManager();
                mMyFastBleManager = myFastBleManager;
            }
        }
        return myFastBleManager;
    }

    /**
     * 设置扫描的监听
     * @param l
     */
    public void setScanEventListener(ScanEventListener l) {
        mListener = l;
    }

    
    /**
     * 读写的监听
     * @param l
     */
    public void setWriteAndReadEventListener(WriteAndReadEventListener l) {
        mWriteAndReadEventListener = l;
    }
    
    
    /**
     * 设置扫描规则
     * @param isAutoConnect
     */
    public void setScanRule(String name, String mac, boolean isAutoConnect) {
        if (!TextUtils.isEmpty(name)) {
            mBuilder.setDeviceName(true, name);
        }
        if (!TextUtils.isEmpty(mac)) {
            mBuilder.setDeviceMac(mac);
        }
        mBuilder.setAutoConnect(isAutoConnect);
        BleScanRuleConfig build = mBuilder.setScanTimeOut(10000).build();
        BleManager.getInstance().initScanRule(build);
    }

    /**
     * 开始扫描
     */
    public void startScan() {
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                if (mListener != null) {
                    mListener.onScanStarted(success);
                }
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                if (mListener != null) {
                    mListener.onScanning(bleDevice);
                }
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                if (mListener != null) {
                    mListener.onScanFinished(scanResultList);
                }
            }
        });
    }

    /**
     * 连接设备
     * @param bleDevice
     */
    
    public boolean connectBle(BleDevice bleDevice) {
        disConnect(bleDevice);
        BleManager.getInstance().cancelScan();
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                if (mListener != null) {
                    mListener.onStartConnect();
                }
                if (mWriteAndReadEventListener != null) {
                    mWriteAndReadEventListener.onStartConnect();
                }
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                if (mListener != null) {
                    mListener.onConnectFail(bleDevice,exception);
                }
                if (mWriteAndReadEventListener != null) {
                    mWriteAndReadEventListener.onConnectFail(bleDevice,exception);
                }
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                if (mListener != null) {
                    mListener.onConnectSuccess(bleDevice,gatt,status);
                }
                if (mWriteAndReadEventListener != null) {
                    mWriteAndReadEventListener.onConnectSuccess(bleDevice,gatt,status);
                }
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                if (mListener != null) {
                    mListener.onDisConnected(isActiveDisConnected,bleDevice,gatt,status);
                }
                if (mWriteAndReadEventListener != null) {
                    mWriteAndReadEventListener.onDisConnected(isActiveDisConnected,bleDevice,gatt,status);
                }
            }
        });
        return true;
    }

    /**
     * 断开连接
     */
    private void disConnect(BleDevice bleDevice){
        if (BleManager.getInstance().isConnected(bleDevice)) {
            BleManager.getInstance().disconnect(bleDevice);
        }
    }

    /**
     * 销毁蓝牙
     */
    public void destoryAllDevice(){
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }

    /*------------------写操作------------------*/
   
    public void writeData(BleDevice bleDevice, byte[] data){
        Log.e("print", "开始写入: "+ByteUtil.bytes2HexStr(data) );
        BleManager.getInstance().write(bleDevice, BleInterface.UUID_WRITE_SERVICE, BleInterface.UUID_WRITE,
            data, new BleWriteCallback() {
                @Override
                public void onWriteSuccess(int current, int total, byte[] justWrite) {
                    if(mWriteAndReadEventListener!=null){
                        mWriteAndReadEventListener.onWriteSuccess(current,total,justWrite);
                    }
                }

                @Override
                public void onWriteFailure(BleException exception) {
                    if(mWriteAndReadEventListener!=null){
                        mWriteAndReadEventListener.onWriteFailure(exception);
                    }
                }
            });
    }

    public void notifyData(BleDevice bleDevice){
        BleManager.getInstance().notify(bleDevice, BleInterface.UUID_NOTIFY_SERVICE, BleInterface.UUID_NOTIFY,
            new BleNotifyCallback() {
                @Override
                public void onNotifySuccess() {
                    if(mWriteAndReadEventListener!=null){
                        mWriteAndReadEventListener.onNotifySuccess();
                    }
                }

                @Override
                public void onNotifyFailure(BleException exception) {
                    if(mWriteAndReadEventListener!=null){
                        mWriteAndReadEventListener.onNotifyFailure(exception);
                    }
                }

                @Override
                public void onCharacteristicChanged(byte[] data) {
                    if(mWriteAndReadEventListener!=null){
                        mWriteAndReadEventListener.onCharacteristicChanged(data);
                    }
                }
            });
    }
    
    public void recycleScanEventListener(){
        mListener = null;
    }
}
