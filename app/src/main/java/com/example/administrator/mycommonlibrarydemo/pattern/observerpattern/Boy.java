package com.example.administrator.mycommonlibrarydemo.pattern.observerpattern;

import android.util.Log;

/**
 * Created by wuyufeng    on  2019/3/6 0006.
 * 观察者实现类
 */

public class Boy implements ObserverListener {
    
    @Override
    public void update(Object data) {
        Log.e("print", "Boy: "+((Weather)data).getDes());
    }
}
