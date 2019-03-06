package com.example.administrator.mycommonlibrarydemo.pattern.observerpattern;

/**
 * Created by wuyufeng    on  2019/3/6 0006.
 * 被观察者
 */

public interface ObservableListener<T> {
    void add(ObserverListener<T> observerListener);
    void remove(ObserverListener<T> observerListener);
    void notifyObserver(T data);
}
