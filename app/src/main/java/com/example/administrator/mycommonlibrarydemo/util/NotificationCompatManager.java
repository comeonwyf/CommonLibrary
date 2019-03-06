package com.example.administrator.mycommonlibrarydemo.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;

import static android.app.NotificationManager.IMPORTANCE_NONE;

/**
 * @Create_time: 2018/11/11 9:54
 * @Author: wr
 * @Description: ${TODO}(NotificationCompatManager系统工具类)
 */

public class NotificationCompatManager {

    private NotificationCompatManager(Context context, Builder builder) {
        sendMessage(context, builder);
    }

    private void sendMessage(Context cx, Builder build){
        if(TextUtils.isEmpty(build.mChannelId)){
            throw new NullPointerException("channelId not is null");
        }
        if(!isOpenMessageChannel(cx,build.mChannelId)){
            return;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(cx, build.mChannelId);
        builder.setSmallIcon(build.mSmallIcon);
        if(build.mPendingIntent != null){
            builder.setContentIntent(build.mPendingIntent);
        }
        if(build.mDefaults != Integer.MIN_VALUE){
            builder.setDefaults(build.mDefaults);
        }
        if(build.mWhen > 0){
            builder.setWhen(build.mWhen);
        }
        if (!TextUtils.isEmpty(build.mTitle)){
            builder.setContentTitle(build.mTitle);
        }
        if(!TextUtils.isEmpty(build.mContent)){
            builder.setContentText(build.mContent);
        }
        if(!TextUtils.isEmpty(build.mTickerText)){
            builder.setTicker(build.mTickerText);
        }
        if(build.mLargeIcon > 0){
            Bitmap bitmap = BitmapFactory.decodeResource(cx.getResources(), build.mLargeIcon);
            builder.setLargeIcon(bitmap);
        }
        if(build.mColor > 0){
            builder.setColor(build.mColor);
        }
        builder.setAutoCancel(build.mAutoCancel);
        if(build.mNumber > 0){
            builder.setNumber(build.mNumber);
        }
        if(build.mSound != null){
            if(build.mStreamType > 0){
                builder.setSound(build.mSound, build.mStreamType);
            } else {
                builder.setSound(build.mSound);
            }
        }
        if(build.mStyle != null){
            builder.setStyle(build.mStyle);
        }
        if(build.mPri != Integer.MIN_VALUE) {
            builder.setPriority(build.mPri);
        }
        if(build.mLightsColor > 0){
            builder.setLights(build.mLightsColor, build.mOnMs, build.mOffMs);
        }
        if(build.mFullIntent != null) {
            builder.setFullScreenIntent(build.mFullIntent, build.mHighPriority);
        }
        NotificationManager manager = (NotificationManager) cx.getSystemService(
                Context.NOTIFICATION_SERVICE);
        if(TextUtils.isEmpty(build.mTag)){
            manager.notify(build.mId, builder.build());
        } else {
            manager.notify(build.mTag, build.mId, builder.build());
        }
    }

    /**
     * 安卓8.0创建消息通道
     * @param context
     * @param channelId 创建消息通道Id
     * @param channelName 创建消息通道消息名称
     * @param importance 创建消息通道的级别
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context, String channelId,
                                                 String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager manager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    /**
     * 判断8.0是否打开了消息通道
     * @param context
     * @param channelId 创建消息通道Id
     */
    public static boolean isOpenMessageChannel(Context context, String channelId){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel(channelId);
            if (channel == null || channel.getImportance() == IMPORTANCE_NONE) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否打开了消息通知
     * @param context
     */
    public static boolean isOpenMessageNotify(Context context){
        NotificationManagerCompat notification = NotificationManagerCompat.from(context);
        return notification.areNotificationsEnabled();
    }

    /**
     * 8.0上设置消息角标数量
     * @param context
     * @param channelId 创建消息通道Id
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void setBadgeNumber(Context context, String channelId, int number){
        NotificationManager manager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setNumber(number)
                .build();
        manager.notify(2, notification);
    }

    /**
     * 取消指定id的通知
     * @param context
     * @param id 通知id
     */
    public static void cancel(Context context, int id){
        NotificationManager manager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        manager.cancel(id);
    }

    /**
     * 取消指定id的通知
     * @param context
     * @param tag 通知tag
     * @param id 通知id
     */
    public static void cancel(Context context, String tag, int id){
        NotificationManager manager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        manager.cancel(tag, id);
    }

    /**
     * 取消所有通知
     * @param context
     */
    public static void cancelAll(Context context){
        NotificationManager manager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    public static class Builder{
        //消息通道Id
        private String mChannelId;
        //设置小图标
        private @DrawableRes int mSmallIcon;
        //通知栏点击的action
        private PendingIntent mPendingIntent;
        //提醒方式
        private int mDefaults = Integer.MIN_VALUE;
        //提示时间
        private long mWhen;
        //提示标题
        private String mTitle;
        //提示内容
        private String mContent;
        //第一次通知书显示
        private String mTickerText;
        //大图片
        private @DrawableRes int mLargeIcon;
        //设置大图的颜色
        private @ColorInt int mColor;
        //通知tag
        private String mTag;
        //通知id
        private int mId;
        //设置是否自动取消
        private boolean mAutoCancel;
        //设置数量
        private int mNumber;
        //设置通知铃声
        private Uri mSound;
        //设置通知铃声的Type
        private int mStreamType;
        //设置通知栏的风格
        private NotificationCompat.Style mStyle;
        //设置闪光灯
        private @ColorInt int mLightsColor;
        private int mOnMs;
        private int mOffMs;
        //设置提醒的级别
        private int mPri = Integer.MIN_VALUE;
        //设置消息一接收到，就跳转到该页面上去
        private PendingIntent mFullIntent;
        private boolean mHighPriority;

        public Builder(String channelId){
            mChannelId = channelId;
        }

        public Builder setSmallIcon(@DrawableRes int smallIcon) {
            mSmallIcon = smallIcon;
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingIntent) {
            mPendingIntent = pendingIntent;
            return this;
        }

        public Builder setDefaults(int defaults) {
            mDefaults = defaults;
            return this;
        }

        public Builder setWhen(long when) {
            mWhen = when;
            return this;
        }

        public Builder setContentTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContentText(String content) {
            mContent = content;
            return this;
        }

        public Builder setTicker(String tickerText){
            mTickerText = tickerText;
            return this;
        }

        public Builder setLargeIcon(@DrawableRes int largeIcon) {
            mLargeIcon = largeIcon;
            return this;
        }

        public Builder setColor(@ColorInt int color) {
            mColor = color;
            return this;
        }

        public Builder setTag(String tag) {
            mTag = tag;
            return this;
        }

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setAutoCancel(boolean autoCancel) {
            mAutoCancel = autoCancel;
            return this;
        }

        public Builder setNumber(int number) {
            mNumber = number;
            return this;
        }

        public Builder setSound(Uri sound) {
            mSound = sound;
            return this;
        }

        public Builder setSound(Uri sound, int streamType) {
            mSound = sound;
            mStreamType = streamType;
            return this;
        }

        public Builder setStyle(NotificationCompat.Style style) {
            mStyle = style;
            return this;
        }

        public Builder setLights(@ColorInt int argb, int onMs, int offMs){
            mLightsColor = argb;
            mOnMs = onMs;
            mOffMs = offMs;
            return this;
        }

        public Builder setPriority(int pri){
            mPri = pri;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent fullIntent, boolean highPriority){
            this.mFullIntent = fullIntent;
            mHighPriority = highPriority;
            return this;
        }

        public NotificationCompatManager build(Context context){
            return new NotificationCompatManager(context, this);
        }
    }
}
