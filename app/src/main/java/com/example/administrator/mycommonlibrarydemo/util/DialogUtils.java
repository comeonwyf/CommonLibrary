package com.example.administrator.mycommonlibrarydemo.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.example.administrator.mycommonlibrarydemo.R;
import java.lang.reflect.Field;

/**
 * @auther: 吴锐
 * @date: 2018-12-03 10:01
 * @describe: dialog工具类
 */
public class DialogUtils {

    //创建一个自定义dialog
    public static AlertDialog.Builder build(Context context){
        return new AlertDialog.Builder(context, R.style.AlertDialogTheme);
    }

    //创建一个dialog
    public static AlertDialog build(Context context, @StringRes int titleId,
                                        @StringRes int contentId){
        String title = context.getString(titleId);
        String content = context.getString(contentId);
        return build(context, title, content);
    }

    //创建一个dialog
    public static AlertDialog build(Context context, String title, String content){
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                                .setTitle(title)
                                .setMessage(content)
                                .create();
        dialog.show();
        return dialog;
    }

    //创建一个dialog
    public static AlertDialog build(Context context, @StringRes int titleId,
                                        @StringRes int contentId, String positiveText,
                                        OnClickListener positiveListener){
        String title = context.getString(titleId);
        String content = context.getString(contentId);
        return build(context, title, content, positiveText, positiveListener);
    }

    //创建一个dialog
    public static AlertDialog build(Context context, String title, String content,
                                        String positiveText, OnClickListener positiveListener){
       AlertDialog dialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                                .setTitle(title)
                                .setMessage(content)
                                .setPositiveButton(positiveText, positiveListener)
                                .create();
        dialog.show();
        return dialog;
    }

    //创建一个dialog
    public static AlertDialog build(Context context, @StringRes int titleId,
                                     @StringRes int contentId, String positiveText,
                                     OnClickListener positiveListener, String negativeText,
                                     OnClickListener negativeListener){
        String title = context.getString(titleId);
        String content = context.getString(contentId);
        return build(context, title, content, positiveText, positiveListener, negativeText,
                        negativeListener);
    }

    //创建一个dialog
    public static AlertDialog build(Context context, String title, String content,
                                        String positiveText, OnClickListener positiveListener,
                                        String negativeText, OnClickListener negativeListener){
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                                .setTitle(title)
                                .setMessage(content)
                                .setPositiveButton(positiveText, positiveListener)
                                .setNegativeButton(negativeText, negativeListener)
                                .create();
        dialog.show();
        return dialog;
    }

    //创建一个dialog
    public static AlertDialog build(Context context, @StringRes int titleId,
                                     @StringRes int contentId, String positiveText,
                                     OnClickListener positiveListener, String negativeText,
                                     OnClickListener negativeListener, String neutralText,
                                     OnClickListener neutralListener){
        String title = context.getString(titleId);
        String content = context.getString(contentId);
        return build(context, title, content, positiveText, positiveListener, negativeText,
                        negativeListener, neutralText, neutralListener);
    }

    //创建一个dialog
    public static AlertDialog build(Context context, String title, String content,
                                     String positiveText, OnClickListener positiveListener,
                                     String negativeText, OnClickListener negativeListener,
                                     String neutralText, OnClickListener neutralListener){
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                                .setTitle(title)
                                .setMessage(content)
                                .setPositiveButton(positiveText, positiveListener)
                                .setNegativeButton(negativeText, negativeListener)
                                .setNeutralButton(neutralText, neutralListener)
                                .create();
        dialog.show();
        return dialog;
    }

    //显示一个View
    public static AlertDialog build(Context context, View view){
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.CustomAlertDialogTheme)
                                    .setView(view)
                                    .create();
        dialog.show();
        return dialog;
    }

    /**
     * 控制dialog是否能够关闭
     * @param dialog 想要控制的dialog
     * @param isCloseAble true表示可以关闭 false表示不能关闭
     */
    public static void setDialogCloseAbility(DialogInterface dialog, boolean isCloseAble) {
        try {
            Field field = dialog.getClass().getSuperclass()
                .getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, isCloseAble);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
