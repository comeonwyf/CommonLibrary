package com.example.widgetlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.Toast;
import com.example.widgetlibrary.R;

/**
 * Created by wuyufeng    on  2018/9/21 0021.
 * （目前只支持中间弹出和底部弹出Dialog）
 */

public  abstract class ICustomDialog extends Dialog {

    private final Context mContext;

    public ICustomDialog(@NonNull Context context) {
        super(context, R.style.CommonDialogStyle);
        mContext = context;
        DialogUtil.adjustDialogLayout(this, isMatchParent(), false);
        DialogUtil.setGravity(this, getGravityStatus());
        setContentView(getLayoutId());
        if(Gravity.BOTTOM == getGravityStatus()){
            //从底部动画弹出
            getWindow().setWindowAnimations(R.style.dialogfrombottom);
        }
        initView();
    }
    
    //获取布局id
    public abstract int getLayoutId() ;
    //设置弹出位置（目前只支持中间弹出和底部弹出）
    public abstract int getGravityStatus();
    //设置宽度是否match parent
    public abstract boolean isMatchParent();
    //设置获取控件，监听等
    public void initView(){
        
    }
    //请用此方法进行show Dialog
    public void showDialog(){
        if(getGravityStatus()!=Gravity.BOTTOM && getGravityStatus()!=Gravity.CENTER){
            Toast.makeText(mContext,"只能设置中间和底部弹出dialog",Toast.LENGTH_SHORT).show();
            return;
        }
        show();
    }
    
}
