package com.example.widgetlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by wuyufeng    on  2018/10/15 0015.
 * interface by
 */

public class ShoppingSelectNumView extends LinearLayout {

    private ImageView mIvSubstract;
    private ImageView mIvAdd;
    private EditText mEtNum;
    private boolean mIsSetRepertoryCount = false;
    private int mRepertoryCount;
    private SelectCountListener mListener;

    public ShoppingSelectNumView(Context context) {
        this(context,null);
    }

    public ShoppingSelectNumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShoppingSelectNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_shopping_select_num,this);
        mIvSubstract = findViewById(R.id.iv_subtract);
        mIvAdd = findViewById(R.id.iv_add);
        mEtNum = findViewById(R.id.et_num);

        //默认字体颜色，大小
        int defaultContentColor = ContextCompat.getColor(context,R.color.select_num_default_color);
        float defaultTextSize = getResources().getDimension(R.dimen.normal_28sp);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShoppingSelectNumView);
        
        //设置字体内容，颜色，大小，是否能编辑
        int color = ta.getColor(R.styleable.ShoppingSelectNumView_select_num_content_color,defaultContentColor);
        mEtNum.setTextColor(color);
        float size = ta.getDimension(R.styleable.ShoppingSelectNumView_select_num_content_size,defaultTextSize);
        mEtNum.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        boolean selectable = ta.getBoolean(R.styleable.ShoppingSelectNumView_select_num_editable,true);
        
        if(!selectable){//不能编辑
            mEtNum.setEnabled(false);
            mEtNum.setFocusable(false);
            mEtNum.setKeyListener(null);
        }else {
            mEtNum.setCursorVisible(false);
            mEtNum.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEtNum.setCursorVisible(true);
                }
            });
        }

        //设置减号
        int substractImageRes = ta.getResourceId(R.styleable.ShoppingSelectNumView_select_num_subtract_img, 0);
        if (substractImageRes != 0) {
            mIvSubstract.setImageResource(substractImageRes);
        }

        //设置加号
        int addImageRes = ta.getResourceId(R.styleable.ShoppingSelectNumView_select_num_add_img, 0);
        if (addImageRes != 0) {
            mIvAdd.setImageResource(addImageRes);
        }
        
        
        setListener(context);
        
    }

    private void setListener(final Context context) {
        mIvSubstract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String countStr = mEtNum.getText().toString().trim();
                if(!TextUtils.isEmpty(countStr)){
                    Integer count = Integer.valueOf(countStr);
                    if(count>1){
                        count--;
                        mEtNum.setText(count+"");  
                    }
                }else {
                    mEtNum.setText("1");
                    if(mIsSetRepertoryCount){
                        if(mRepertoryCount==0){
                            mEtNum.setText("0");
                        }
                    }
                }
                mEtNum.setSelection(mEtNum.getText().toString().length());
                if(mListener!=null){
                    mListener.getCount(mEtNum.getText().toString().trim());
                }
            }
        });
        
        mIvAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String countStr = mEtNum.getText().toString().trim();
                if(!TextUtils.isEmpty(countStr)){
                    Integer count = Integer.valueOf(countStr);
                    count++;
                    mEtNum.setText(count+"");
                    if(mIsSetRepertoryCount){
                        if(mRepertoryCount<count){
                            if(mListener!=null){
                                mListener.overInventoryTip();
                            }
                            mEtNum.setText(mRepertoryCount+"");
                        }
                    }
                }else {
                    mEtNum.setText("1");
                    if(mIsSetRepertoryCount){
                        if(mRepertoryCount==0){
                            mEtNum.setText("0");
                        }
                    }
                }
                mEtNum.setSelection(mEtNum.getText().toString().length());
                if(mListener!=null){
                    mListener.getCount(mEtNum.getText().toString().trim());
                }
            }
        });
        
        mEtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String countStr = s.toString();
                if(countStr.length()>1){
                    String substring1 = countStr.substring(0,1);
                    String substring2 = countStr.substring(1,2);
                    if(substring1.equals("0")){
                        mEtNum.setText(substring2);
                        mEtNum.setSelection(1);
                    }
                }
                
                if(mIsSetRepertoryCount && !TextUtils.isEmpty(countStr)){
                    if(mRepertoryCount<Integer.valueOf(countStr)){
                        if(mListener!=null){
                            mListener.overInventoryTip();
                        }
                        mEtNum.setText(mRepertoryCount+"");
                        mEtNum.setSelection(mEtNum.getText().toString().length());
                    }
                }
                if(mListener!=null){
                    mListener.getCount(mEtNum.getText().toString().trim());
                }
            }
        });
        
        
    }
    
    
    /**
     * 设置商品的数量
     * @param count
     */
    public void setShoppingCount(int count){
        mEtNum.setText(count+"");
        mEtNum.setSelection(mEtNum.getText().toString().length());
    }

    /**
     * 设置库存的数量
     * @param repertoryCount
     */
    public void setRepertoryCount(int repertoryCount){
        mIsSetRepertoryCount = true;
        mRepertoryCount = repertoryCount;
    }

    /**
     * 获取最后的数量(这里返回字符串，防止输入空，请在外部进行判断)
     */
    public String getShoppingCount(){
        String countStr = mEtNum.getText().toString().trim();
        return countStr;
    }

    /**
     * 选择数量回调
     * 当有设置库存的时候，超过库存数的时候回调
     */
    public interface SelectCountListener{
        void getCount(String count);
        void overInventoryTip();
    }
    
    public void setSelectCountListener(SelectCountListener l){
        mListener = l;
    }
}
