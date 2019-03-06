package com.example.administrator.mycommonlibrarydemo.pattern.observerpattern;

/**
 * Created by wuyufeng    on  2019/3/6 0006.
 * 观察者监听
 */

public interface ObserverListener<T> {
    public void update(T data);
}
