package com.example.administrator.mycommonlibrarydemo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.administrator.mycommonlibrarydemo.util.ToastUtil;
import com.example.widgetlibrary.dialog.ICustomDialog;

/**
 * Created by wuyufeng    on  2018/7/26 0026.
 * 继承ICustomDialog
 */

public class CustomDialog extends ICustomDialog {

    private final Context mContext;

    public CustomDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_custom;
    }

    @Override
    public int getGravityStatus() {
        return Gravity.BOTTOM;
    }

    @Override
    public boolean isMatchParent() {
        return true;
    }

    @Override
    public void initView() {
        findViewById(R.id.tv_women).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastShort(mContext,"我是女生");
            }
        });
    }
}
