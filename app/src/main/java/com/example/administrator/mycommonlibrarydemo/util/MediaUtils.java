package com.example.administrator.mycommonlibrarydemo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.ruyishangwu.utils.bean.MediaBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther: 吴锐
 * @date: 2018-11-27 11:41
 * @describe: 多媒体工具类
 */
public class MediaUtils {

    // 获取本地图片
    public static List<MediaBean> getLocationImage(Context context){
        ContentResolver resolver = context.getContentResolver();
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.MIME_TYPE};
        Cursor cursor = resolver.query(imageUri, projection, null, null,
            MediaStore.Images.Media.DATE_ADDED);
        if(cursor != null){
            List<MediaBean> mediaBeans = new ArrayList<>();
            while (cursor.moveToNext()){
                // 获取图片路径
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                //获取图片名称
                String columnName = MediaStore.Images.Media.DISPLAY_NAME;
                String name = cursor.getString(cursor.getColumnIndex(columnName));
                //获取图片类型
                columnName = MediaStore.Images.Media.MIME_TYPE;
                String type = cursor.getString(cursor.getColumnIndex(columnName));
                //获取图片时间
                columnName = MediaStore.Images.Media.DATE_ADDED;
                long time = cursor.getLong(cursor.getColumnIndex(columnName));
                //过滤未下载完成或者不存在的文件
                if (!"downloading".equals(getExtensionName(path)) && checkImgExists(path)) {
                    mediaBeans.add(new MediaBean(path, name, type, time));
                }
            }
            cursor.close();
            return mediaBeans;
        }
        return null;
    }

    //获取本地视频
    public static List<MediaBean> getLocationVideo(Context context){
        ContentResolver resolver = context.getContentResolver();
        Uri imageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.MIME_TYPE};
        Cursor cursor = resolver.query(imageUri, projection, null, null,
            MediaStore.Video.Media.DATE_ADDED);
        if(cursor != null){
            List<MediaBean> mediaBeans = new ArrayList<>();
            while (cursor.moveToNext()){
                // 获取图片路径
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                //获取图片名称
                String columnName = MediaStore.Video.Media.DISPLAY_NAME;
                String name = cursor.getString(cursor.getColumnIndex(columnName));
                //获取图片类型
                columnName = MediaStore.Video.Media.MIME_TYPE;
                String type = cursor.getString(cursor.getColumnIndex(columnName));
                //获取图片时间
                columnName = MediaStore.Video.Media.DATE_ADDED;
                long time = cursor.getLong(cursor.getColumnIndex(columnName));
                //过滤未下载完成或者不存在的文件
                if (!"downloading".equals(getExtensionName(path)) && checkImgExists(path)) {
                    mediaBeans.add(new MediaBean(path, name, type, time));
                }
            }
            cursor.close();
            return mediaBeans;
        }
        return null;
    }

    private static String getExtensionName(String path) {
        if (!TextUtils.isEmpty(path)) {
            int dot = path.lastIndexOf('.');
            if (dot > -1 && dot < path.length() - 1) {
                return path.substring(dot + 1);
            }
        }
        return "";
    }

    private static boolean checkImgExists(String filePath) {
        return new File(filePath).exists();
    }
}
