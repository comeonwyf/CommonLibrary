package com.example.httplibrary.http.callback;

import com.example.httplibrary.http.util.Bean01Convert;

/**
 * 处理了我们接口那种code=0，code=1情况的回调
 */

public abstract class Bean01Callback<T> extends MyCallback<T> {

    /**
     * 处理了我们接口那种code=0，code=1情况的回调
     *
     * @param clazz
     */
    public Bean01Callback(Class<T> clazz) {
        setClass(clazz);
    }

    public Bean01Callback() {
    }

    @Override
    protected T convert(String response) throws Throwable {
        return new Bean01Convert<>(mClazz).convert(response);
    }
}
