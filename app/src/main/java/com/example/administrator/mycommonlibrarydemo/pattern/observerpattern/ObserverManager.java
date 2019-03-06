package com.example.administrator.mycommonlibrarydemo.pattern.observerpattern;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyufeng    on  2019/3/6 0006.
 * 观察者管理类（单例）
 */

public class ObserverManager<T> implements ObservableListener<T>{

    private  Context mContext;
    private static ObserverManager mObserverManager;
    private List<ObserverListener<T>> list = new ArrayList<>();
    
    private ObserverManager (Context context) {
        mContext = context;
    }
    
    public static ObserverManager getInstance(Context context){
        if(mObserverManager == null){
           synchronized (ObserverManager.class){
               mObserverManager = new ObserverManager(context);  
            }
        }
        return mObserverManager;
    }

    @Override
    public void add(ObserverListener<T> observerListener) {
        list.add(observerListener);
    }

    @Override
    public void remove(ObserverListener<T> observerListener) {
        list.remove(observerListener);
    }

    @Override
    public void notifyObserver(T data) {
        for (ObserverListener<T> tObserverListener : list) {
            tObserverListener.update(data);
        }
    }
    
}
