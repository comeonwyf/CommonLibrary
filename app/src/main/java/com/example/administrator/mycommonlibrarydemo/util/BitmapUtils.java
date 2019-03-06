package com.example.administrator.mycommonlibrarydemo.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther: 吴锐
 * @date: 2018-11-27 16:50
 * @describe: 图片工具类
 */
public class BitmapUtils {
    // 采用率压缩
    public static Bitmap compressAdopt(String srcPath, int desWidth, int desHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //不加载图片，只获取宽高信息
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        int width = options.outWidth;
        int height = options.outHeight;

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (width > height && width > desHeight) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (width / desWidth);
        } else if (width < height && height > desHeight) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (height / desHeight);
        }
        if(be > 1) {
            options.inSampleSize = be;
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(srcPath, options);
    }

    //尺寸压缩
    public static Bitmap compressSize(String srcPath, int desWidth, int desHeight) {
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath);
        return compressSize(bitmap, desWidth, desHeight);
    }

    //尺寸压缩
    public static Bitmap compressSize(Bitmap srcBitmap, int desWidth, int desHeight) {
        return Bitmap.createScaledBitmap(srcBitmap, desWidth, desHeight, true);
    }

    // 质量压缩
    public static Bitmap compressQuality(Bitmap bitmap, int size){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        bitmap.compress(format, quality, baos);
        while (baos.toByteArray().length / 1024 > size) {
            quality -= 10;
            baos.reset();
            bitmap.compress(format, quality, baos);
        }
        byte[] data = baos.toByteArray();
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    // 质量压缩保存在本地
    public static File compressQualityToFile(Bitmap bitmap, int size)throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fos = null;
        File file = null;
        try {
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            int quality = 100;
            bitmap.compress(format, quality, baos);
            while (baos.toByteArray().length / 1024 > size) {
                quality -= 10;
                baos.reset();
                bitmap.compress(format, quality, baos);
            }
            byte[] data = baos.toByteArray();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_SS");
            File parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            String name = sdf.format(new Date()) + ".jpg";
            file = new File(parent, name);
            fos = new FileOutputStream(file);
            fos.write(data);
        }catch (Exception e){
            throw e;
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
    /**
     * 将bitmap保存在本地
     * @param bitmap 图片
     * @param path 文件路径，不含文件名
     */
    public static File saveBitmapToFile(Bitmap bitmap, String path) throws Exception{
        FileOutputStream fos = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_SS");
        String name = sdf.format(new Date()) + ".jpg";
        File parent = new File(path);
        if(!parent.exists()){
            parent.mkdirs();
        }
        File file = new File(parent, name);
        try {
            fos = new FileOutputStream(file);
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            bitmap.compress(format, 100, fos);
            fos.flush();
        } catch (Exception e){
            throw e;
        } finally {
            if(fos != null){
                fos.close();
            }
        }
        return file;
    }

    /**
     * 将bitmap保存在本地 通知相册
     * @param bitmap 图片
     * @param path 文件路径，不含文件名
     */
    public static File saveBitmapToFile(Context context, Bitmap bitmap, String path)
        throws Exception{
        FileOutputStream fos = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_SS");
        String name = sdf.format(new Date()) + ".jpg";
        File parent = new File(path);
        if(!parent.exists()){
            parent.mkdirs();
        }
        File file = new File(parent, name);
        try {
            fos = new FileOutputStream(file);
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            bitmap.compress(format, 100, fos);
            fos.flush();
            //保存成功后插入到图库，其中的file是保存成功后的图片path。这里只是插入单张图片
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
        } catch (Exception e){
            throw e;
        } finally {
            if(fos != null){
                fos.close();
            }
        }
        return file;
    }
}
