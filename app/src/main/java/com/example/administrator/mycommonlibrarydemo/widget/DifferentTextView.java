package com.example.administrator.mycommonlibrarydemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.mycommonlibrarydemo.R;

/**
 * Created by steve   on  2018/7/13 0013.
 * 用于显示不同大小颜色的组合textView(最多三个)
 */

public class DifferentTextView extends LinearLayout {
    private TextView mLeftContent;
    private TextView mMiddleContent;
    private TextView mRightContent;

    public DifferentTextView(Context context) {
        this(context,null);
    }

    public DifferentTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DifferentTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_different_text, this, true);
        mLeftContent = view.findViewById(R.id.tv_left);
        mMiddleContent = view.findViewById(R.id.tv_middle);
        mRightContent = view.findViewById(R.id.tv_right);
        
        //默认字体颜色，大小,不同textview间距
        int defaultContentColor = ContextCompat.getColor(context,R.color.different_text_view_content_color);
        float defaultMargin = getResources().getDimension(R.dimen.normal_0dp);
        float defaultTextSize = getResources().getDimension(R.dimen.normal_28sp);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DifferentTextView);
        
        //设置颜色
        int color;
        color = ta.getColor(R.styleable.DifferentTextView_leftContent_color_different_view,defaultContentColor);
        mLeftContent.setTextColor(color);
        color = ta.getColor(R.styleable.DifferentTextView_middleContent_color_different_view,defaultContentColor);
        mMiddleContent.setTextColor(color);
        color = ta.getColor(R.styleable.DifferentTextView_rightContent_color_different_view,defaultContentColor);
        mRightContent.setTextColor(color);

        //设置大小
        float size;
        size = ta.getDimension(R.styleable.DifferentTextView_leftContent_size_different_view,defaultTextSize);
        mLeftContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        size = ta.getDimension(R.styleable.DifferentTextView_middleContent_size_different_view,defaultTextSize);
        mMiddleContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        size = ta.getDimension(R.styleable.DifferentTextView_rightContent_size_different_view,defaultTextSize);
        mRightContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        
        //设置间距
        float margin;
        LinearLayout.LayoutParams linearLayout;
        margin = ta.getDimension(R.styleable.DifferentTextView_leftContent_marginRight_different_view,defaultMargin);
        linearLayout = (LayoutParams) mLeftContent.getLayoutParams();
        linearLayout.rightMargin = (int)margin;
        mLeftContent.setLayoutParams(linearLayout);
        margin = ta.getDimension(R.styleable.DifferentTextView_rightContent_marginLeft_different_view,defaultMargin);
        linearLayout = (LayoutParams) mRightContent.getLayoutParams();
        linearLayout.leftMargin = (int)margin;
        mRightContent.setLayoutParams(linearLayout);
        
        //是否是粗体
        boolean isBold;
        TextPaint tp;
        isBold = ta.getBoolean(R.styleable.DifferentTextView_leftContent_show_bold_different_view,false);
        if(isBold){
            tp = mLeftContent.getPaint();
            tp.setFakeBoldText(true); 
        }
        isBold = ta.getBoolean(R.styleable.DifferentTextView_middleContent_show_bold_different_view,false);
        if(isBold){
            tp = mMiddleContent.getPaint();
            tp.setFakeBoldText(true);
        }
        isBold = ta.getBoolean(R.styleable.DifferentTextView_rightContent_show_bold_different_view,false);
        if(isBold){
            tp = mRightContent.getPaint();
            tp.setFakeBoldText(true);
        }

        //是否显示第三个textview
        boolean isShowRight;
        isShowRight = ta.getBoolean(R.styleable.DifferentTextView_rightContent_show_different_view,false);
        if(isShowRight){
            mRightContent.setVisibility(GONE);
        }
        ta.recycle();
    }
    
    //设置左边textview
    public void setLeftContent(int resId){
        mLeftContent.setText(getResources().getString(resId));
    }
    public void setLeftContent(String leftContent){
        mLeftContent.setText(leftContent);
    }

    //设置中间textview
    public void setMiddleContent(int resId){
        mMiddleContent.setText(getResources().getString(resId));
    }
    public void setMiddleContent(String middleContent){
        mMiddleContent.setText(middleContent);
    }

    //设置右边textview
    public void setRightContent(int resId){
        mRightContent.setText(getResources().getString(resId));
    }
    public void setRightContent(String rightContent){
        mRightContent.setText(rightContent);
    }
    
}
