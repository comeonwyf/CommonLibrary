package com.example.administrator.mycommonlibrarydemo.http.interf;

/**
 * Created by wuyufeng    on  2018/7/9 0009.
 * interface by
 */

public interface ErrorInterceptor {

    /**
     * 拦截异常，
     *
     * @param tr 异常
     * @return 如果返回true，则表示异常被拦截住，不会走{@link MyCallback#onFailure(java.lang.String, * java.lang.Throwable)} 或者 {@link OkObserver#onFailure(java.lang.String, java.lang.Throwable)}
     */
    boolean interceptException(Throwable tr);
}
