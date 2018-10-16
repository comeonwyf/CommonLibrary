package com.example.widgetlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import com.example.widgetlibrary.R;

/**
 * Created by wuyufeng    on  2018/9/21 0021.
 * interface by
 */

public class ICustomDialog extends Dialog {

    public ICustomDialog(@NonNull Context context) {
        super(context, R.style.CommonDialogStyle);

        //DialogUtil.adjustDialogLayout(this, true, false);
        //DialogUtil.setGravity(this, Gravity.BOTTOM);
        //
        //setContentView(R.layout.dialog_custom);
        //
        ////是否从底部动画弹出
        //getWindow().setWindowAnimations(R.style.dialogfrombottom);
        
    }
    
    
}
