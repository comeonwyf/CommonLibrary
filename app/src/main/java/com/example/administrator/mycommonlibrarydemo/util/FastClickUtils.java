package com.example.administrator.mycommonlibrarydemo.util;

import android.support.annotation.IdRes;

/**
 * @auther: 吴锐
 * @date: 2018-12-06 09:25
 * @describe: 用于处理按钮过快点击
 */
public class FastClickUtils {
    //上一次点击的时间
    public static long sPrevTime;
    //处理View的id
    @IdRes
    public static int sViewId;
    //默认点击时间1秒
    public static final int DEFAULT_CLICK_TIME = 1000;

    //默认1s才能进行点击
    public static boolean isFastClick(@IdRes int viewId){
        return isFastClick(viewId, DEFAULT_CLICK_TIME);
    }

    //指定多少毫秒可以点击该按钮
    public static boolean isFastClick(@IdRes int viewId, long intervalTime){
        if(viewId != sViewId){
            sViewId = viewId;
            sPrevTime = 0;
        }
        long currentTime = System.currentTimeMillis();
        boolean isClick = false;
        if(currentTime - sPrevTime >= intervalTime){
            isClick = true;
        }
        sPrevTime = currentTime;
        return isClick;
    }
}
