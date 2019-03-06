package com.example.administrator.mycommonlibrarydemo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;

/**
 * @Create_time: 2018/11/11 9:54
 * @Author: wr
 * @Description: ${TODO}(VibratorUtil震动工具类)
 */

public class VibratorUtils {
    /**
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    @SuppressLint("MissingPermission")
    public static void vibrate(final Context cx) {
        long milliseconds = 100;
        Vibrator vib = (Vibrator) cx.getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    @SuppressLint("MissingPermission")
    public static void vibrate(final Context cx, long milliseconds) {
        Vibrator vib = (Vibrator) cx.getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    @SuppressLint("MissingPermission")
    public static void vibrate(final Context cx, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) cx.getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);//-1为反复震动
    }
}
