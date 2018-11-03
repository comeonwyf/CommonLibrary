package com.example.widgetlibrary.util;

import android.util.Log;

/**
 * Created by wuyufeng    on  2018/11/3 0003.
 * 防止按钮被快速点击
 */

public class ButtonUtil {
    
    private static long lastClickTime = 0;
    private static long DIFF = 300;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于DIFF，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于DIFF，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            Log.e("print", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
}
