package com.example.httplibrary.http.interf;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by wuyufeng    on  2018/7/9 0009.
 * 请求日志打印
 */

public interface RequestLogger {

    String TAG = "RequestLogger";

    /**
     * 打印响应信息
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param response 响应文本
     * @param tr 异常
     */
    void logRequest(String url, HttpHeaders headers, HttpParams params, String response,
        Throwable tr);
}
