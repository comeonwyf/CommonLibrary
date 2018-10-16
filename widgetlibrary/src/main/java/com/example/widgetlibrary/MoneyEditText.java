package com.example.widgetlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by wuyufeng    on  2018/10/15 0015.
 * 自定义可输入金额的EditText
 */

@SuppressLint("AppCompatCustomView")
public class MoneyEditText extends EditText {
    private static final String TAG = "MoneyEditText";
    private boolean textChange;

    public MoneyEditText(Context context) {
        this(context,null);
    }

    public MoneyEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置可以输入小数
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        setFocusable(true);
        setFocusableInTouchMode(true);

        //监听文字变化
        addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!textChange) {
                    restrictText();
                }
                textChange = false;
            }
        });
        
        
    }

    /**
     * 将小数限制为2位
     */
    private void restrictText() {
        String input = getText().toString();
        if (TextUtils.isEmpty(input)) {
            return;
        }
        if (input.contains(".")) {
            int pointIndex = input.indexOf(".");
            int totalLenth = input.length();
            int len = (totalLenth - 1) - pointIndex;
            if (len > 2) {
                input = input.substring(0, totalLenth - 1);
                textChange = true;
                setText(input);
                setSelection(input.length());
            }
        }

        //处理以.为结束的格式
        if (input.toString().trim().substring(0).equals(".")) {
            input = "0" + input;
            setText(input);
            setSelection(2);
        }

        //处理0开头的数据
        if(input.length()>1 && !input.contains(".")){
            String substring1 = input.substring(0,1);
            String substring2 = input.substring(1,2);
            if(substring1.equals("0")){
                setText(substring2);
                setSelection(1);
            }
        }
    }

    /**
     * 获取金额(请根据返回的金额是否为"0.00",来判断是否为0金额)
     */
    public String getMoneyText() {
        String money = getText().toString();
        
        //如果最后一位是小数点
        if (money.endsWith(".")) {
            money = money.substring(0, money.length() - 1);
        }
        
        if("0".equals(money) || "0.0".equals(money) || TextUtils.isEmpty(money)){
           money = "0.00"; 
        }
        return money;
    }
    
}
