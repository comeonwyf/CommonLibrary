package com.example.administrator.mycommonlibrarydemo.pattern.observerpattern;

import android.util.Log;

/**
 * Created by wuyufeng    on  2019/3/6 0006.
 * 观察者实现类
 */

public class Girl<T> implements ObserverListener<T> {

    @Override
    public void update(T data) {
        Log.e("print", "Girl: "+data.toString() );
    }
}
