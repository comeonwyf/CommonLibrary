package com.example.administrator.mycommonlibrarydemo.example.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.administrator.mycommonlibrarydemo.widget.DifferentTextView;
import com.example.administrator.mycommonlibrarydemo.widget.PayTypeView;

public class CustomViewActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
      
        setDifferentTextView();
        
        setPayTypeView();
        
    }


    //设置DifferentTextView
    private void setDifferentTextView() {
        DifferentTextView  mItemOne = findViewById(R.id.item_one);
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
