package com.example.widgetlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wuyufeng    on  2018/7/16 0016.
 * 选择支付方式的item
 */

public class PayTypeView extends FrameLayout {

    private ImageView mIvLabel;
    private TextView mTvContent;
    private ImageView mIvSelect;
    private ImageView mIvUnselect;
    private int mSelectImageRes;
    private int mUnselectImageRes;

    public PayTypeView(Context context) {
        this(context,null);
    }

    public PayTypeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PayTypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pay_type, this, true);
        mIvLabel = view.findViewById(R.id.iv_label);
        mTvContent = view.findViewById(R.id.tv_content);
        mIvSelect = view.findViewById(R.id.iv_select);
        mIvUnselect = view.findViewById(R.id.iv_unselect);
        
        //默认字体颜色，大小
        int defaultContentColor = ContextCompat.getColor(context,R.color.paytype_view_content_color);
        float defaultTextSize = getResources().getDimension(R.dimen.normal_28sp);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PayTypeView);

        //设置字体内容，颜色，大小
        String content = ta.getString(R.styleable.PayTypeView_content_paytype_view);
        mTvContent.setText(content);
        int color = ta.getColor(R.styleable.PayTypeView_content_color_paytype_view,defaultContentColor);
        mTvContent.setTextColor(color);
        float size = ta.getDimension(R.styleable.PayTypeView_content_size_paytype_view,defaultTextSize);
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        
        //设置label
        int labelImageRes = ta.getResourceId(R.styleable.PayTypeView_label_paytype_view, 0);
        if (labelImageRes != 0) {
            mIvLabel.setImageResource(labelImageRes);
        }
        float labelSize = ta.getDimension(R.styleable.PayTypeView_label_size_paytype_view,0);
        if(labelSize!=0){
            ViewGroup.LayoutParams layoutParams = mIvLabel.getLayoutParams();
            layoutParams.height = (int) labelSize;
            layoutParams.width = (int) labelSize;
            mIvLabel.setLayoutParams(layoutParams);
        }
        
        //获取选中或未选中的图片资源
        mSelectImageRes = ta.getResourceId(R.styleable.PayTypeView_select_img_paytype_view, 0);
        if (mSelectImageRes != 0) {
            mIvSelect.setImageResource(mSelectImageRes);
        }
        mIvSelect.setVisibility(GONE);
        mUnselectImageRes = ta.getResourceId(R.styleable.PayTypeView_unselect_img_paytype_view, 0);
        if (mUnselectImageRes != 0) {
            mIvUnselect.setImageResource(mUnselectImageRes);
        }
        ta.recycle();
    }
    
    //是否选中状态
    public boolean getSelectStatus(){
        return mIvSelect.getVisibility() == VISIBLE ? true : false;
    }
    
    //设置是否选中
    public void setSelectType(boolean isSelected){
        if(isSelected){
            mIvSelect.setVisibility(VISIBLE);
            mIvUnselect.setVisibility(GONE);
        }else {
            mIvSelect.setVisibility(GONE);
            mIvUnselect.setVisibility(VISIBLE);
        }
    }

    //设置content
    public void setContent(int resId){
        mTvContent.setText(getResources().getString(resId));
    }
    public void setContent(String middleContent){
        mTvContent.setText(middleContent);
    }
    
    
}
