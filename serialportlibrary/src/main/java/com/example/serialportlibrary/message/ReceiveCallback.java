package com.example.serialportlibrary.message;

/**
 * Created by wuyufeng    on  2018/9/7 0007.
 * interface by
 */

public interface ReceiveCallback {
    void onReceive(String devicePath,String baudrateString,byte[] received, int size);
}
