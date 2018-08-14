package com.example.administrator.mycommonlibrarydemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.administrator.mycommonlibrarydemo.util.DialogUtil;

/**
 * Created by wuyufeng    on  2018/7/26 0026.
 * interface by
 */

public class CustomDialog extends Dialog {
    
    public CustomDialog(@NonNull Context context) {
        this(context,0);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.CommonDialogStyle);
        
        DialogUtil.adjustDialogLayout(this, true, false);
        DialogUtil.setGravity(this, Gravity.BOTTOM);

        setContentView(R.layout.dialog_custom);
        
        //是否从底部动画弹出
        getWindow().setWindowAnimations(R.style.dialogfrombottom);
        
        
        
    }
    
    
}
