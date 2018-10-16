package com.example.administrator.mycommonlibrarydemo.example.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.administrator.mycommonlibrarydemo.util.ThreeMapsUtil;
import com.example.administrator.mycommonlibrarydemo.util.ToastUtil;
import com.example.administrator.mycommonlibrarydemo.widget.CustomDialog;
import com.example.widgetlibrary.ColorSeekBar;
import com.example.widgetlibrary.DifferentTextView;
import com.example.widgetlibrary.MoneyEditText;
import com.example.widgetlibrary.PayTypeView;
import com.example.widgetlibrary.ShoppingSelectNumView;

public class CustomViewActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        mContext = this;
      
        
        setDifferentTextView();//不同字体的view
        
        setPayTypeView();//支付item
        
        setDialog();
        
        setRecyclerView();
        
        setColorSeekBar();
        
        setMap();
        
        setMoneyEditText();

        setShoppingSelectNumView();
    }

    private void setShoppingSelectNumView() {
        ShoppingSelectNumView shoppingSelectNumView = findViewById(R.id.shoppingSelectNumView);
        shoppingSelectNumView.setShoppingCount(10);
        shoppingSelectNumView.setSelectCountListener(new ShoppingSelectNumView.SelectCountListener() {
            @Override
            public void getCount(String count) {
                ToastUtil.showToastShort(getApplicationContext(),count);
            }
        });
    }

    private void setMoneyEditText() {
       MoneyEditText moneyEditText = findViewById(R.id.moneyEditText);
        Log.e("print", "得到金额: "+moneyEditText.getMoneyText() );
    }

    private void setMap() {
        findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ThreeMapsUtil().toGaoDeMapByAddress(CustomViewActivity.this,"深圳市南山区科兴科学园");
            }
        });
    }

    private void setColorSeekBar() {
        ColorSeekBar colorSeekBar = findViewById(R.id.colorSeekBar);
        colorSeekBar.setColorByGadient(3, true,0,new int[]{Color.WHITE,Color.RED},new int[]{Color.WHITE,Color.BLUE},new int[]{Color.RED,Color.BLACK});
        colorSeekBar.setOnStateChangeListener(new ColorSeekBar.OnStateChangeListener() {
            @Override
            public void OnStateChangeListener(float progress) {
                Log.e("print", "OnStateChangeListener: "+progress );
            }

            @Override
            public void onStopTrackingTouch(float progress) {
                Log.e("print", "onStopTrackingTouch: "+progress );
            }
        });
        ColorSeekBar colorSeekBar1 = findViewById(R.id.colorSeekBar1);
        colorSeekBar1.setColor(4,true,100/4*3, Color.RED,Color.BLACK,Color.BLUE,Color.YELLOW);
    }

    private void setRecyclerView() {
        findViewById(R.id.btn_recyclerView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RecyclerViewDemoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setDialog() {
        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                //用法1
                //ICustomDialog iCustomDialog = new ICustomDialog(mContext) {
                //    @Override
                //    public int getLayoutId() {
                //        return R.layout.dialog_custom;
                //    }
                //
                //    @Override
                //    public int getGravityStatus() {
                //        return Gravity.BOTTOM;
                //    }
                //
                //    @Override
                //    public boolean isMatchParent() {
                //        return true;
                //    }
                //};
                //iCustomDialog.showDialog();
                
                //用法2

                CustomDialog customDialog  = new CustomDialog(mContext);
                customDialog.showDialog();
                
            }
        });
    }

    //设置DifferentTextView
    private void setDifferentTextView() {
        DifferentTextView mItemOne = findViewById(R.id.item_one);
        DifferentTextView mItemTwo = findViewById(R.id.item_two);
        
        mItemOne.setLeftContent("我是左边的内容内容内容内容");
        mItemOne.setMiddleContent("/娃娃");

        mItemTwo.setLeftContent("我是左边");
        mItemTwo.setMiddleContent("/娃娃我是左边");
        mItemTwo.setRightContent(R.string.app_name);
        
    }


    private void setPayTypeView() {
       final PayTypeView itemPay =  findViewById(R.id.item_pay);
       itemPay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(itemPay.getSelectStatus()){
                   itemPay.setSelectType(false);
               }else {
                   itemPay.setSelectType(true);
               }
           }
       });
    }
}
