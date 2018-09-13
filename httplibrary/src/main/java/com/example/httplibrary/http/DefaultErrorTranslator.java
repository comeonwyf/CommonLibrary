package com.example.httplibrary.http;

import com.example.httplibrary.http.interf.ErrorTranslator;
import com.google.gson.JsonParseException;
import com.lzy.okgo.exception.HttpException;
import java.net.UnknownHostException;

/**
 * Created by wuyufeng    on  2018/7/9 0009.
 * 默认提供的异常信息格式化器
 */

public class DefaultErrorTranslator implements ErrorTranslator {
    @Override
    public String translate(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return "服务器异常";
        } else if (throwable instanceof JsonParseException) {
            // json解析错误，一般就是接口改了
            return "网络接口异常(maybe json解析有误)";
        } else if (throwable instanceof UnknownHostException) {
            return "检查网络是否打开";
        }else if (throwable instanceof ApiException) {
            return throwable.getMessage();
        } else {
            return throwable.getMessage();
        }
    }
}
