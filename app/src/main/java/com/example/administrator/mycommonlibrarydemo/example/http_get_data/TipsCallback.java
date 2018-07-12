package com.example.administrator.mycommonlibrarydemo.example.http_get_data;

import com.example.administrator.mycommonlibrarydemo.http.ApiException;
import com.example.administrator.mycommonlibrarydemo.http.callback.MyCallback;
import com.example.administrator.mycommonlibrarydemo.http.util.Convert2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by wuyufeng    on  2018/5/22 0022.
 * interface by
 */

public abstract class TipsCallback<T> extends MyCallback<T> {

    private static final Gson sGson = new GsonBuilder().serializeNulls().create();

    public TipsCallback(Class<T> clazz) {
        setClass(clazz);
    }

    public TipsCallback() {
    }

    @Override
    protected T convert(String stringResponse) throws Throwable {

        JsonElement jsonElement = Convert2.toJsonElement(stringResponse);

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int code = jsonObject.get("code").getAsInt();
        String msg = jsonObject.get("tip").getAsString();

        if (code == 1) {
            T t = sGson.fromJson(jsonElement, mClazz);
            return t;
        } else {
            throw new ApiException(msg, code);
        }
        
    }
}
