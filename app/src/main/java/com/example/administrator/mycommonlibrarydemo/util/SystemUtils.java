package com.example.administrator.mycommonlibrarydemo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @Create_time: 2018/11/11 9:54
 * @Author: wr
 * @Description: ${TODO}(系统工具类)
 */

public class SystemUtils {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号 6.0
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号 HUAWEI MI7-TL00
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商 Huawei
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI 865**********25
     */
    @SuppressLint("MissingPermission")
    public static String getImei(Context cx) {
        TelephonyManager tm = (TelephonyManager) cx.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            imei = tm.getImei();
        } else {
            imei = tm.getDeviceId();
        }
        return imei == null ? "" : imei;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI 865**********25
     */
    @SuppressLint("MissingPermission")
    public static String getImei(Context cx, int slotIndex) {
        TelephonyManager tm = (TelephonyManager) cx.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            imei = tm.getImei(slotIndex);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imei = tm.getDeviceId(slotIndex);
        } else {
            imei = tm.getDeviceId();
        }
        return imei == null ? "" : imei;
    }

    //获取IMSI
    @SuppressLint("MissingPermission")
    public static String getImsi(Context cx){
        TelephonyManager tm = (TelephonyManager) cx.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        return imsi == null ? "" : imsi;
    }

    //综上述，AndroidId 和 Serial Number 的通用性都较好，并且不受权限限制，如果刷机和恢复出厂设置
    //会导致设备标识符重置这一点可以接受的话，那么将他们组合使用时，唯一性就可以应付绝大多数设备了。
    //获取android唯一id
    public static String getAndroidId(Context context){
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return androidId + Build.SERIAL;
    }

    // 判断android系统是否被root了
    public static boolean isRoot(Context context){
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }


    private static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su",
            "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su",
            "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su",
            "/su/bin/su"};
        for (String path : paths) {
            try {
                if (new File(path).exists()) {
                    return true;
                }
            } catch (Exception e){

            }
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null){
                return true;
            }
        } catch (Throwable t) {

        } finally {
            if (process != null){
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 判断定位是否开启(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 如果开启返回ture，否则返回false
     *
     * 如果要监听定位开关状态可以使用广播来实现  Action为LocationManager.PROVIDERS_CHANGED_ACTION
     */
    public static boolean isLocationEnabled(Context cx) {
        ContentResolver resolver = cx.getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int locationMode;
            try {
                locationMode = Secure.getInt(resolver, Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false; 
            }
             return locationMode != Secure.LOCATION_MODE_OFF;
        }
        String provider = Secure.getString(resolver, Secure.LOCATION_PROVIDERS_ALLOWED);
        return !TextUtils.isEmpty(provider);
    }

    //获取当前的运营商 不使用移动网络时，运营商获取的也为null
    @SuppressLint("MissingPermission")
    public static String getOperator(Context cx) {
        TelephonyManager manager = (TelephonyManager) cx.getSystemService(
            Context.TELEPHONY_SERVICE);
        String imsi = manager.getSubscriberId();
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002") ||
                imsi.startsWith("46007")) {
                return "中国移动";
            } else if (imsi.startsWith("46001")  || imsi.startsWith("46006")) {
                return "中国联通";
            } else if (imsi.startsWith("46003")) {
                return "中国电信";
            }
        }
        return "";
    }

    /**
     *
     *  <uses-permission android:name="android.permission.GET_TASKS" />
     *  <uses-permission android:name="android.permission.REAL_GET_TASKS" />
     */
    /**
     * [判断进程是否存在]
     *
     * @param cx
     * @param processName 进程名称
     * @return true存在 false不存在
     */
    public static boolean isRunningTaskExist(Context cx,String processName){
        ActivityManager am=(ActivityManager) cx.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info:processList){
            if (info.processName.equals(processName)){
                return true;
            }
        }
        return false;
    }

    //是否安装
    public static boolean isInstalled(Context context, String packageName) {
        boolean installed = false;
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        List<ApplicationInfo> infos = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo info : infos) {
            if (packageName.equals(info.packageName)) {
                installed = true;
                break;
            } else {
                installed = false;
            }
        }
        return installed;
    }

    // 安装apk适配android7.0之后
    public static boolean installApk(Context context, File file) {
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkUri = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            String authority = "com.ruyishangwu.userapp.FileProvider";
            apkUri = FileProvider.getUriForFile(context, authority, file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            apkUri = Uri.fromFile(file);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        context.startActivity(intent);
        return true;
    }

    //卸载应用
    public static boolean uninstallApk(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    //是否是系统app
    public static boolean isSystemApp(Context context, String packageName) {
        boolean isSys = false;
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            if (info != null && (info.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                isSys = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            isSys = false;
        }
        return isSys;
    }

    //获取mac地址
    public static String getMac(Context context){
        String mac = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mac = getMacFromHardware();
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            mac = getMacAddress();
        } else {
            mac = getMacDefault(context);
        }
        return mac == null ? "" : mac;
    }


    /**
     * android 7.0有效
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     * @return
     */
    private static String getMacAddress() {
        try {
            String pathName = "/sys/class/net/wlan0/address";
            return new BufferedReader(new FileReader(new File(pathName))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * @param context
     * @return
     */
    private static String getMacDefault(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            try {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {
                    return info.getMacAddress();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }

    //跳转应用系统设置
    public static void goToAppSystemSetting(Activity activity, int code){
        Uri uri = Uri.parse("package:" + activity.getPackageName());
        Intent intent = new Intent()
            .setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(uri);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, code);
    }

    //android 8.0跳转app安装未知来源app权限
    public static void goToAppUnInstall(Activity activity, int code){
        //跳转安装打开权限页面
        Uri uri = Uri.parse("package:" + activity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
        activity.startActivityForResult(intent, code);
    }
}
