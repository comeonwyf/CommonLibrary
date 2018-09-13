package com.example.administrator.mycommonlibrarydemo.example.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.administrator.mycommonlibrarydemo.widget.CustomDialog;
import com.example.widgetlibrary.ColorSeekBar;
import com.example.widgetlibrary.DifferentTextView;
import com.example.widgetlibrary.PayTypeView;

public class CustomViewActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        mContext = this;
      
        setDifferentTextView();
        
        setPayTypeView();
        
        setDialog();
        
        setRecyclerView();
        
        setColorSeekBar();
        
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
                CustomDialog dialog = new CustomDialog(mContext);
                dialog.show();
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
