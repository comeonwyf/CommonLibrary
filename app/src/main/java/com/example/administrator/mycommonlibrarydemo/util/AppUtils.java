package com.example.administrator.mycommonlibrarydemo.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.os.Build;
import android.os.Debug;
import android.os.Looper;
import android.text.TextUtils;
import java.security.MessageDigest;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * @Created_time: 2018/3/9 10:14
 * @Author: wr
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

public class AppUtils {

    private AppUtils() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    //获取当前apk的名称
    public static String getAppName(Context context){
        return getAppName(context, context.getPackageName());
    }

    /**
     * 获取指定报名应用的名称
     */
    public static String getAppName(Context cx, String packageName) {
        try {
            PackageManager pm = cx.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return cx.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取当前app版本号
    public static String getVersionName(Context context){
        return getVersionName(context, context.getPackageName());
    }

    /**
     *
     * @param cx
     * @return 获取指定报名的版本名称
     */
    public static String getVersionName(Context cx, String packageName) {
        try {
            PackageManager packageManager = cx.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    ////获取当前apk版本
    //public static long getVersionCode(Context context){
    //    return getVersionCode(context, context.getPackageName());
    //}

    ///**
    // * 获取指定包名的apk版本号
    // */
    //public static long getVersionCode(Context cx, String packageName){
    //    try {
    //        PackageManager packageManager = cx.getPackageManager();
    //        PackageInfo info = packageManager.getPackageInfo(packageName, 0);
    //        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
    //            return info.getLongVersionCode();
    //        }
    //        return info.versionCode;
    //    } catch (PackageManager.NameNotFoundException e) {
    //        e.printStackTrace();
    //    }
    //    return -1;
    //}

    /**
     * 获取当前应用包名
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取显示在最顶端的activity名称
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager manager = (ActivityManager) (context.getSystemService(ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> infos = manager.getRunningTasks(1);
        if (infos != null && infos.size() > 0) {
            ComponentName f = infos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }

    /**
     * 判断是否运行在前台
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        String packageName = context.getPackageName();
        String topActivityClassName = getTopActivityName(context);
        if (packageName != null && topActivityClassName != null
            && topActivityClassName.startsWith(packageName)) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前进程名
     */
    private String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                return process.processName;
            }
        }
        return null;
    }

    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    public boolean isMainProcess(Context context) {
        return getPackageName(context).equals(getCurrentProcessName(context));
    }

    /**
     * 判断是否为主线程
     */
    public static boolean isMainThread(){
        return Looper.getMainLooper() == Looper.myLooper();
    }

    //判断当前app是否有安装权限 8.0
    public static boolean isInstall(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }

    //判断是否应用抓包
    public static boolean isKissNTell(){
        if(!TextUtils.isEmpty(System.getProperty("http.proxyHost")) ||
            !TextUtils.isEmpty(System.getProperty("http.proxyPort"))){
            return true;
        }
        return false;
    }

    //判断是否应用被调试 防止反编译将AndroidManifest.xml 中application中将android:debuggable属性值设置为true
    public static boolean isDebug(Context context){
        return 0 != (context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE);
    }

    //判断应用是否在调试连接状态
    public static boolean isDebugConnect(){
        return Debug.isDebuggerConnected();
    }

    ///**
    // * 获取当前app的包名
    // * @param context
    // */
    //public static String getAppSign(Context context){
    //    return getAppSign(context, context.getPackageName());
    //}

    ///**
    // * 获取指定包名的apk签名
    // * @param context
    // * @param packageName
    // */
    //public static String getAppSign(Context context, String packageName) {
    //    try {
    //        PackageManager pm = context.getPackageManager();
    //        Signature[] signs = null;
    //        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
    //            PackageInfo info = pm.getPackageInfo(packageName, GET_SIGNING_CERTIFICATES);
    //            SigningInfo signingInfo = info.signingInfo;
    //            signs = signingInfo.getApkContentsSigners();
    //        } else {
    //            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
    //            signs = info.signatures;
    //        }
    //        return signatureMD5(signs);
    //    } catch (PackageManager.NameNotFoundException e) {
    //        e.printStackTrace();
    //    }
    //    return "";
    //}

    /**
     * 返回MD5
     */
    protected static String signatureMD5(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return CipherUtils.parseByte2HexStr(digest.digest());
        } catch (Exception e) {
        }
        return "";
    }
}
