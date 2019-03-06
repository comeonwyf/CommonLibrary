package com.example.administrator.mycommonlibrarydemo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED;

/**
 * @Create_time: 2018/11/11 9:54
 * @Author: wr
 * @Description: ${TODO}(NetworkUtils系统工具类)
 */

public class NetworkUtils {

    // 判断当前网络是否可用
    @SuppressLint("MissingPermission")
    public static boolean networkIsConnect(Context cx) {
        boolean isConnection = isNetworkConnected(cx);
        boolean isUsable = false;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ConnectivityManager manager = (ConnectivityManager) cx.getSystemService(
                Context.CONNECTIVITY_SERVICE);
            Network network = manager.getActiveNetwork();
            if(network != null){
                NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);
                isUsable = capabilities.hasCapability(NET_CAPABILITY_VALIDATED);
            }
        } else {
            isUsable = isNetworkOnline();
        }
        return isConnection && isUsable;
    }

    //判断网络是否连接
    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context cx) {
        ConnectivityManager manager = (ConnectivityManager)cx.getSystemService(
            Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    //android 6.0一下判断网络是否可用
    public static boolean isNetworkOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("ping -c 3 www.baidu.com");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 判断当前连接的网络是否是wifi
    @SuppressLint("MissingPermission")
    public static boolean isNetworkIsWifi(Context cx) {
        ConnectivityManager conn = (ConnectivityManager) cx.getSystemService(
            Context.CONNECTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Network network = conn.getActiveNetwork();
            if(network != null){
                NetworkCapabilities capabilities = conn.getNetworkCapabilities(network);
                boolean isConnection = capabilities.hasCapability(NET_CAPABILITY_VALIDATED);
                boolean isWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                return isConnection && isWifi;
            }
        } else {
            NetworkInfo info = conn.getActiveNetworkInfo();
            if (info != null) {
                if(info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        }
        return false;
    }

    // 是否打开Wifi
    @SuppressLint("MissingPermission")
    public static void setWifiEnabled(Context cx, boolean enabled) {
        WifiManager wifiManager = (WifiManager) cx.getSystemService(Context.WIFI_SERVICE);
        if (enabled) {
            wifiManager.setWifiEnabled(true);
        } else {
            wifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    //获取当前设备ip
    @SuppressLint("MissingPermission")
    public static String getIPAddress(Context cx) {
        if (isNetworkIsWifi(cx)) {
            WifiManager wifiManager = (WifiManager) cx.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
            return ipAddress;
        } else {
            try {
                Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                while (en.hasMoreElements()) {
                    NetworkInterface face = en.nextElement();
                    if (face != null) {
                        Enumeration<InetAddress> address = face.getInetAddresses();
                        while (address.hasMoreElements()) {
                            InetAddress inetAddress = address.nextElement();
                            if (!inetAddress.isAnyLocalAddress() &&
                                inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

     //将得到的int类型的IP转换为String类型
     public static String intIP2StringIP(int ip) {
         return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." +
         (ip >> 24 & 0xFF);
     }

     //获取当前手机网络类型
    @SuppressLint("MissingPermission")
    public static String getNetworkType(Context cx) {
        ConnectivityManager manager = (ConnectivityManager) cx.getSystemService(
            Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Network network = manager.getActiveNetwork();
            if(network != null && info != null && info.isConnected()) {
                NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return "WIFI";
                } else {
                    return getNetworkType(info.getSubtype(), info.getSubtypeName());
                }
            }
        } else {

            if(info != null && info.isConnected()){
                if(info.getType() == ConnectivityManager.TYPE_WIFI){
                    return "WIFI";
                } else {
                    return getNetworkType(info.getSubtype(), info.getSubtypeName());
                }
            }
        }
        return "";
    }

    private static String getNetworkType(int type, String subTypeName){
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
            case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
            case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                return "4G";
        }
        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
        if (subTypeName.equalsIgnoreCase("TD-SCDMA") ||
            subTypeName.equalsIgnoreCase("WCDMA") ||
            subTypeName.equalsIgnoreCase("CDMA2000")) {
            return "3G";
        }
        return subTypeName;
    }
}
