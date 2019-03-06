package com.example.administrator.mycommonlibrarydemo.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.lang.reflect.Field;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * @Create_time: 2018/11/11 9:54
 * @Author: wr
 * @Description: ${TODO}(键盘工具类)
 */

public class KeyBoardUtils {
    /**
     * 打开软键盘
     * @param cx 上下文
     */
    public static void openKeyboard(Context cx, View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.findFocus();
        InputMethodManager imm = (InputMethodManager) cx.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭软键盘
     * @param cx 上下文
     */
    public static void closeKeyboard(Context cx, View view) {
        InputMethodManager imm = (InputMethodManager) cx.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // 解决部分手机EditText出现的内存泄漏
    public void inputMethodDestroy(Context cx){
        if(cx == null){
            return;
        }
        InputMethodManager im = (InputMethodManager) cx.getSystemService(INPUT_METHOD_SERVICE);
        if(im == null){
            return;
        }
        // mLastSrvView 出现在华为手机中
        String[] strFields = new String[]{"mCurRootView", "mServedView", "mNextServedView",
            "mLastSrvView"};
        Class clazz = InputMethodManager.class;
        for (String strField : strFields) {
            try {
                Field field = clazz.getDeclaredField(strField);
                field.setAccessible(true);
                Object obj = field.get(im);
                if(obj != null && obj instanceof View){
                    View view = (View) obj;
                    if(view.getContext() == cx){
                        field.set(im, null);
                    } else {
                        break;
                    }
                }
            } catch (Exception e){

            }
        }
    }
}
