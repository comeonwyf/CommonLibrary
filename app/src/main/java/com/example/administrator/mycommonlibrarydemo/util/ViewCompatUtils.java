package com.example.administrator.mycommonlibrarydemo.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

/**
 * @auther: 吴锐
 * @date: 2018-12-07 11:02
 * @describe: 用于兼容tint着色器
 */
public class ViewCompatUtils {
    //着色器兼容5.0以下
    public static void setViewBackgroundTint(Context context, View view,
                                             @DrawableRes int drawableId, @ColorRes int resId){
        //获得相关的Drawable
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if(drawable == null){
            throw new RuntimeException("drawable can't null");
        }
        //获得ConstantState状态
        Drawable.ConstantState state = drawable.getConstantState();
        //这里我的理解是 如果该drawable有状态，就创建一个新的drawable出来
        drawable = state == null ? drawable : state.newDrawable().mutate();
        Drawable tintDrawable = DrawableCompat.wrap(drawable);
        ColorStateList tintList = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tintList = context.getColorStateList(resId);
        } else {
            tintList = context.getResources().getColorStateList(resId);
        }
        DrawableCompat.setTintList(tintDrawable, tintList);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(tintDrawable);
        } else {
            view.setBackgroundDrawable(tintDrawable);
        }
    }

    //着色器兼容5.0以下
    public static void setViewBackgroundTint(Context context, View view, @ColorRes int resId){
        //获得相关的Drawable
        Drawable drawable = view.getBackground();
        if(drawable == null){
            throw new RuntimeException("drawable can't null");
        }
        //获得ConstantState状态
        Drawable.ConstantState state = drawable.getConstantState();
        //这里我的理解是 如果该drawable有状态，就创建一个新的drawable出来
        drawable = state == null ? drawable : state.newDrawable().mutate();
        Drawable tintDrawable = DrawableCompat.wrap(drawable);
        ColorStateList tintList = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tintList = context.getColorStateList(resId);
        } else {
            tintList = context.getResources().getColorStateList(resId);
        }
        DrawableCompat.setTintList(tintDrawable, tintList);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(tintDrawable);
        } else {
            view.setBackgroundDrawable(tintDrawable);
        }
    }
}
