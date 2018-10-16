package com.example.administrator.mycommonlibrarydemo.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by wuyufeng    on  2018/9/22 0022.
 * 调用第三方地图（高德或百度），如果都没有安装，就调用高德的网页地图
 */

public class ThreeMapsUtil {
    private static String GaoDePackageName = "com.autonavi.minimap";
    private static String BaiduPackageName = "com.baidu.BaiduMap";

    /**
     * 根据详细地址跳转到高德地图
     * @param detailAddress 包括省市县的详细地址 例如广东省东莞市南城区太子酒店
     */
    public void toGaoDeMapByAddress(Activity activity, String detailAddress) {
        toGaoDeMap(activity, null, null, detailAddress);
    }

    /**
     * 根据经纬度跳转到高德地图
     * @param lat 纬度
     * @param lng 经度
     */
    public void toGaoDeMapByLatAndLng(Activity activity, Double lat, Double lng) {
        toGaoDeMap(activity, lat, lng, null);
    }

    /**
     * 跳转到高德地图
     * @param lat
     * @param lng
     * @param detailAddress
     */
    private void toGaoDeMap(Activity activity, Double lat, Double lng, String detailAddress) {
        try {
            if (isAvailable(activity, GaoDePackageName)) {//安装了高德地图app
                if (lat == null && lng == null) {//根据详细地址跳转
                    String act = "android.intent.action.VIEW";
                    String dat = "androidamap://keywordNavi?sourceApplication=softname&keyword=" + detailAddress + " &style=2";
                    String pkg = "com.autonavi.minimap";
                    Intent intent = new Intent(act, Uri.parse(dat));
                    intent.setPackage(pkg);
                    activity.startActivity(intent);
                } else {//根据经纬度跳转
                    Intent intents = new Intent();
                    intents.setData(Uri.parse("androidamap://navi?sourceApplication=nyx_super&lat=" + lat + "&lon=" + lng + "&dev=0&style=2"));
                    activity.startActivity(intents);
                }
            } else {//跳转到高德网页地图
                ToastUtil.showToastShort(activity, "没有安装高德app,正打开网页版");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("http://uri.amap.com/navigation"));
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否安装了某地图
     * @param activity
     * @param mapPackageName
     * @return
     */
    public boolean isAvailable(Activity activity, String mapPackageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(mapPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo == null ? false : true;
    }
}
