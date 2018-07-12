package com.example.administrator.mycommonlibrarydemo.example.http_get_data;

import com.example.administrator.mycommonlibrarydemo.http.OkGoWrapper;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by wuyufeng    on  2018/7/11 0011.
 * interface by
 */

public class MainHttp {
    
    private final OkGoWrapper mOkGoWrapper;

    private MainHttp() {
        mOkGoWrapper = OkGoWrapper.instance();
    }

    private static class InstanceHolder {
        private static final MainHttp sInstance = new MainHttp();
    }

    public static MainHttp get() {
        return InstanceHolder.sInstance;
    }

  
    public void cooperateApply(int type,TipsCallback<MainBean> callback) {
        HttpParams Params = new HttpParams();
        Params.put("type", type);
        mOkGoWrapper.post(MainUrls.COOPERATE_APPLY, null, Params, MainBean.class, callback);
    }
    
    
    
}
