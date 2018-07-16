package com.example.administrator.mycommonlibrarydemo.example.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.administrator.mycommonlibrarydemo.widget.DifferentTextView;

public class CustomViewActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
      
        setDifferentTextView();
        
    }

    //设置DifferentTextView
    private void setDifferentTextView() {
        DifferentTextView  mItemOne = findViewById(R.id.item_one);
        DifferentTextView mItemTwo = findViewById(R.id.item_two);
        
        mItemOne.setLeftContent("我是左边的内容内容内容内容");
        mItemOne.setMiddleContent("/娃娃");

        mItemTwo.setLeftContent("我是左边的内容内容内容内容我是左边的内容内容内容内容我是左边的内容内容内容内容我是左边的内容内容内容内容");
        mItemTwo.setMiddleContent("/娃娃");
        mItemTwo.setRightContent("--右边");
    }
}
