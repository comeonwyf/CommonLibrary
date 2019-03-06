package com.example.administrator.mycommonlibrarydemo.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * @Create_time: 2018/11/11 9:54
 * @Author: wr
 * @Description: ${TODO}(ToastUtils工具类)
 */

public class ToastUtils {
    private static Toast sToast;

    public ToastUtils(){
        throw new UnsupportedOperationException("ToastUtils is not create");
    }

    private static void init(Context context, int duration){
        if(sToast == null){
            // 适配小米显示项目名称问题
            sToast = Toast.makeText(context.getApplicationContext(), null, LENGTH_LONG);
        }
        sToast.setDuration(duration);
    }

    public static void showShort(Context context, String content){
        init(context, Toast.LENGTH_SHORT);
        sToast.setText(content);
        sToast.show();
    }

    public static void showShort(Context context, @StringRes int resId){
        init(context, Toast.LENGTH_SHORT);
        sToast.setText(resId);
        sToast.show();
    }

    public static void showLong(Context context, String content){
        init(context, LENGTH_LONG);
        sToast.setText(content);
        sToast.show();
    }

    public static void showLong(Context context, @StringRes int resId){
        init(context, LENGTH_LONG);
        sToast.setText(resId);
        sToast.show();
    }

    public static void show(Context context, String content, int duration){
        init(context, duration);
        sToast.setText(content);
        sToast.show();
    }

    public static void show(Context context, @StringRes int resId, int duration){
        init(context, duration);
        sToast.setText(resId);
        sToast.show();
    }

    public static void cancel(){
        if(sToast != null){
            sToast.cancel();
        }
    }
}
