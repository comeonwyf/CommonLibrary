package com.example.administrator.mycommonlibrarydemo;

import android.app.Application;
import com.example.administrator.mycommonlibrarydemo.http.ApiException;
import com.example.administrator.mycommonlibrarydemo.http.DefaultErrorTranslator;
import com.example.administrator.mycommonlibrarydemo.http.JsonRequestLogger;
import com.example.administrator.mycommonlibrarydemo.http.OkGoWrapper;
import com.example.administrator.mycommonlibrarydemo.http.interf.ErrorInterceptor;

/**
 * Created by wuyufeng    on  2018/7/9 0009.
 * interface by
 */

public class App extends Application{
    private static App sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initOkGo();
    }

    private void initOkGo() {
        OkGoWrapper.initOkGo(sInstance)
            // 错误信息再格式化
            .setErrorTranslator(new DefaultErrorTranslator())
            // 拦截网络错误，一般是登录过期啥的
            .setErrorInterceptor(new ErrorInterceptor() {
                @Override
                public boolean interceptException(Throwable tr) {
                    if (tr instanceof ApiException) {
                        ApiException ex = (ApiException) tr;
                        if (ex.getCode() == 401) {
                            // todo 登录信息过期，请重新登录

                            return true;
                        }
                    }
                    return false;
                }
            })
            // 打印网络访问日志的
            .setRequestLogger(new JsonRequestLogger(true,30));
    }
}
