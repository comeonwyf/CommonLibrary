package com.example.administrator.mycommonlibrarydemo.badge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.example.administrator.mycommonlibrarydemo.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by wuyufeng    on  2019/1/10 0010.
 * interface by
 */

public class BadgeNumManager {
    
    private static BadgeNumManager mBadgeNumManager;
    private Context context;

    private static String SYSTEM_XIAOMI="XIAOMI";
    private static String SYSTEM_SAMSUNG="SAMSUNG";
    private static String SYSTEM_HUAWEI_HONOR="HONOR";
    private static String SYSTEM_HUAWEI="HUAWEI";
    private static String SYSTEM_NOVA="NOVA";
    private static String SYSTEM_SONY="SONY";
    private static String SYSTEM_VIVO="VIVO";
    private static String SYSTEM_OPPO="OPPO";
    private static String SYSTEM_LG="LG";
    private static String SYSTEM_ZUK="ZUK";
    private static String SYSTEM_HTC="HTC";
    
    public static BadgeNumManager getInstance(Context context){
        if(mBadgeNumManager == null){
            mBadgeNumManager = new BadgeNumManager(context.getApplicationContext());
        }
        return mBadgeNumManager;
    }

    private BadgeNumManager(Context context) {
        this.context = context;
    }
    
    
    public void setBadgeNum(int num){
       String OSName = Build.BRAND.trim().toUpperCase();
       if(SYSTEM_HUAWEI_HONOR.equals(OSName) || SYSTEM_HUAWEI.equals(OSName)){
           setHuaWeiBadgeNum(num);
       }else if(SYSTEM_XIAOMI.equals(OSName)) {
           setXiaomiBadgeNum(num);
       }else if(SYSTEM_NOVA.equals(OSName)) {
           setBadgeOfNova(context,num);
       }else if(SYSTEM_ZUK.equals(OSName)) {
           setBadgeOfZuk(context,num);
       }else if(SYSTEM_SONY.equals(OSName)) {
           setBadgeOfSony(context,num);
       }else if(SYSTEM_SAMSUNG.equals(OSName)) {
           setBadgeOfSumsung(context,num);
       }else if(SYSTEM_LG.equals(OSName)) {
           setBadgeOfSumsung(context,num);
       }else if(SYSTEM_HTC.equals(OSName)) {
           setBadgeOfHTC(context,num);
       }else {
           ShortcutBadger.applyCount(context, num) ;
       }
    }
    
    //华为品牌
    private void setHuaWeiBadgeNum(int num){
        try {
            Bundle bunlde = new Bundle();
            bunlde.putString("package", context.getPackageName());
            bunlde.putString("class", getLauncherClassName(context));
            bunlde.putInt("badgenumber", num);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //小米品牌
    private void setXiaomiBadgeNum(int num){
        NotificationManager mNotificationManager = (NotificationManager) context
            .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name",
                NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();//是否绕过请勿打扰模式
            channel.enableLights(false);//闪光灯
            //channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            //channel.setLightColor(Color.RED);//闪关灯的灯光颜色
            //channel.canShowBadge();//桌面launcher的消息角标
            //channel.enableVibration(true);//是否允许震动
            //channel.getAudioAttributes();//获取系统通知响铃声音的配置
            channel.getGroup();//获取通知取到组
            channel.setBypassDnd(true);//设置可绕过  请勿打扰模式
            channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
            //channel.shouldShowLights();//是否会有灯光
            mNotificationManager.createNotificationChannel(channel);
        }
        Notification.Builder builder = new Notification.Builder(context)
            .setContentTitle("如一出行").setContentText("新消息").setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = builder.build();
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification,num );
        } catch (Exception e) {
            e.printStackTrace();
        }
        mNotificationManager.notify(0,notification);
    }

    /**
     * 设置Nova的Badge
     * @paramcontext context
     * @paramcount count
     */

    private void setBadgeOfNova(Context context, int count){
        ContentValues contentValues = new ContentValues();
        contentValues.put( "tag", context.getPackageName() + "/"+
            getLauncherClassName(context));
        contentValues.put( "count", count);
        context.getContentResolver().insert(
            Uri.parse( "content://com.teslacoilsw.notifier/unread_count"),
            contentValues);

    }

    /**
     * 设置联想ZUK的Badge
     * 需要添加权限：<uses-permission android:name="android.permission.READ_APP_BADGE" />
     * @paramcontext
     * @paramcount
     */

    private void setBadgeOfZuk(Context context, int count){
        Bundle extra = new Bundle();
        extra.putInt( "app_badge_count", count);
        context.getContentResolver().call(
            Uri.parse( "content://com.android.badge/badge"),
            "setAppBadgeCount", null, extra);
    }

    /**
     * 设置索尼的Badge
     * 需添加权限：<uses-permission android:name="com.sonymobile.home.permission.PROVIDER_INSERT_BADGE" />
     * <uses-permission android:name="com.sonyericsson.home.permission.BROADCAST_BADGE" />
     * <uses-permission android:name="com.sonyericsson.home.action.UPDATE_BADGE" />
     * @paramcontext context
     * @paramcount count
     */

    private void setBadgeOfSony(Context context, int count){
        String launcherClassName = getLauncherClassName(context);
        if(launcherClassName == null) {
            return;
        }

        boolean isShow = true;
        if(count == 0) {
            isShow = false;
        }

        Intent localIntent = new Intent();
        localIntent.setAction( "com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra( "com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow); // 是否显示
        localIntent.putExtra( "com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName); // 启动页
        localIntent.putExtra( "com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(count)); // 数字
        localIntent.putExtra( "com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName()); // 包名
        context.sendBroadcast(localIntent);

    }

    /**
     * 设置三星的Badge设置LG的Badge
     * 需添加权限：<uses-permission android:name="com.sec.android.provider.badge.permission.READ" />
     * <uses-permission android:name="com.sec.android.provider.badge.permission.WRITE" />
     * @paramcontext context
     * @paramcount count
     */

    private  void setBadgeOfSumsung(Context context, int count){
        // 获取你当前的应用
        String launcherClassName = getLauncherClassName(context);
        if(launcherClassName == null) {
            return;
        }
        Intent intent = new Intent( "android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra( "badge_count", count);
        intent.putExtra( "badge_count_package_name", context.getPackageName());
        intent.putExtra( "badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);

    }

    /**
     * 设置HTC的Badge
     * @paramcontext context
     * @paramcount count
     */

    private void setBadgeOfHTC(Context context, int count){
        Intent intentNotification = new Intent( "com.htc.launcher.action.SET_NOTIFICATION");
        ComponentName localComponentName = new ComponentName(context.getPackageName(), getLauncherClassName(context));
        intentNotification.putExtra( "com.htc.launcher.extra.COMPONENT", localComponentName.flattenToShortString());
        intentNotification.putExtra( "com.htc.launcher.extra.COUNT", count);
        context.sendBroadcast(intentNotification);
        Intent intentShortcut = new Intent( "com.htc.launcher.action.UPDATE_SHORTCUT");
        intentShortcut.putExtra( "packagename", context.getPackageName());
        intentShortcut.putExtra( "count", count);
        context.sendBroadcast(intentShortcut);
    }

    
    
    //获取启动类名
    public static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }
        return info.activityInfo.name;
    }
    
}
