package com.example.administrator.mycommonlibrarydemo.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * @Create_time: 2018/11/11 9:54
 * @Author: wr
 * @Description: ${TODO}(DensityUtils系统工具类)
 */

public class DensityUtils {
    private DensityUtils()
    {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param cx
     * @param dpVal
     * @return
     */
    public static int dp2px(Context cx, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, cx.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param cx
     * @param spVal
     * @return
     */
    public static int sp2px(Context cx, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, cx.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param cx
     * @param pxVal
     * @return
     */
    public static float px2dp(Context cx, float pxVal) {
        float scale = cx.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param cx
     * @param pxVal
     * @return
     */
    public static float px2sp(Context cx, float pxVal) {
        return (pxVal / cx.getResources().getDisplayMetrics().scaledDensity);
    }

}
