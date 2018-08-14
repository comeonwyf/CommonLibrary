package com.example.administrator.mycommonlibrarydemo.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.example.administrator.mycommonlibrarydemo.R;

/**
 * Created by wuyufeng    on  2018/7/26 0026.
 * interface by
 */

public class ToastUtil {
    public static void showCenterToast(Context ctx,String str){
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void showToastLong(Context ctx, String str) {
        Toast.makeText(ctx, str, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context ctx, String str) {
        Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
    }
    
    public static void showCenterCustomToast(Activity activity){
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.view_toast,null);
        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    
}
