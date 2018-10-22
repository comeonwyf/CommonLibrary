package com.example.widgetlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * Created by wuyufeng    on  2018/10/22 0022.
 * 设置倾斜的textview
 */

public class RotateTextView extends android.support.v7.widget.AppCompatTextView {
    private int DEFAULT_DEGREE = 0;
    private int mDegree = 0;
    public RotateTextView(Context context) {
        this(context,null);
    }

    public RotateTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setGravity(Gravity.CENTER);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateTextView);
        mDegree = typedArray.getInteger(R.styleable.RotateTextView_degree_rotate_textview,DEFAULT_DEGREE);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getCompoundPaddingLeft(),getExtendedPaddingTop());
        canvas.rotate(mDegree,getWidth()/2f,getHeight()/2f);
        super.onDraw(canvas);
        canvas.restore();
    }
    
    public void setDegree(int degree){
        mDegree = degree;
        //invalidate();
    }
}
