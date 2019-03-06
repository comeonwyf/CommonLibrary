package com.example.administrator.mycommonlibrarydemo.manager;

import android.content.Context;
import android.util.Log;
import io.minio.MinioClient;

/**
 * Created by wuyufeng    on  2019/3/6 0006.
 * interface by
 */

public class MinioClientManager {
    private Context mContext;
    private static MinioClientManager mMinioClientManager;
    private MinioClient mMinioClient;
    private final static String IP = "http://file.ruyishangwu.com"; // 你的minio服务器地址
    private final static String ACCESSKEY = "E6YPVQGPWP80741ZV6GF"; // 你的minio服务器账号
    private final static String SECRETKEY = "oB0I+8DzHYBVJYfSfsIXHCecaWggtp+VCKZbGNtp"; // 你的minio服务器账号秘钥


    private MinioClientManager(Context context){
        mContext = context;
    }
    
    public static MinioClientManager getInstance(Context context){
        if(mMinioClientManager == null){
            synchronized (MinioClientManager.class){
                mMinioClientManager = new MinioClientManager(context);
            }
        }
        return mMinioClientManager;
    }

    /**
     *
     * @param filePath 上传的目标文件路径
     * @param buckerName 桶的名称（也就是你存在minio某个文件下）
     * @param destinationName 上传后文件的名称
     */
    public void uploadFile(final String filePath, final String buckerName, final String destinationName) throws Exception {
        if(mMinioClient == null) {
            mMinioClient = new MinioClient(IP, ACCESSKEY, SECRETKEY);
        }
        boolean bucketExists = mMinioClient.bucketExists(buckerName);
        if(bucketExists){
            Log.e("print", "--Bucket exists." );
        }else {
            mMinioClient.makeBucket(buckerName);
            Log.e("print", "--Bucket does not exist." );
        }
        mMinioClient.putObject(buckerName, destinationName, filePath);

    }
    
}
