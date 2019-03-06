package com.example.widgetlibrary.fastble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import java.util.List;

/**
 * Created by wuyufeng    on  2018/11/1 0001.
 * interface by
 */

public class LocationManager {

    private Activity mActivity;
    private LocationPermissionListener mListener;

    public LocationManager(Activity activity,LocationPermissionListener l) {
        mListener = l;
        mActivity = activity;
        checkPermissions();
    }

    /**
     * 检查权限
     */
    public void checkPermissions() {

        //先打开蓝牙
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            if (mListener != null) {
                mListener.openBlueToothSetting();
            }
            return;
        }
        //先判断是否有定位权限
        AndPermission.with(mActivity)
            .requestCode(100)
            .permission(Permission.LOCATION)
            .callback(listener)
            .rationale(mRationaleListener)
            .start();
    }

    //定位权限监听
    PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            if (100 == requestCode) {//定位
                if (mListener != null) {
                    mListener.hasPermissions();
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

            if (100 == requestCode) {//定位
                AndPermission.defaultSettingDialog(mActivity, 100)
                    .setTitle("权限申请失败")
                    .setMessage("需定位基本权限,否则您将无法正常使用，请在设置中授权")
                    .setPositiveButton("好，去设置")
                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .show();
            }
        }
    };

    //用户拒绝一次权限后，再次申请时检测到已经申请过一次该权限了，允许开发者弹窗说明申请权限的目的，获取用户的同意后再申请权限，避免用户勾选不再提示，导致不能再次申请权限
    private RationaleListener mRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            if (100 == requestCode) {
                new AlertDialog.Builder(mActivity).setTitle("权限提示")
                    .setCancelable(false)
                    .setMessage("没有定位权限,您某些功能将无法正常使用")
                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();// 用户同意继续申请。
                        }
                    })
                    .setNegativeButton("狠心拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel(); // 用户拒绝申请。
                            if (mListener != null) {
                                mListener.noPermissions();
                            }
                        }
                    })
                    .show();
            }
        }
    };

    public interface LocationPermissionListener{
        public void hasPermissions();
        public void noPermissions();
        public void openBlueToothSetting();
        
    }
    
}
