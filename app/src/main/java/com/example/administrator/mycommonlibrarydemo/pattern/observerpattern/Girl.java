package com.example.administrator.mycommonlibrarydemo.pattern.observerpattern;

import android.util.Log;

/**
 * Created by wuyufeng    on  2019/3/6 0006.
 * 观察者实现类
 */

public class Girl implements ObserverListener<Weather> {

    @Override
    public void update(Weather data) {
        Log.e("print", "update: "+data.des );
    }
}
