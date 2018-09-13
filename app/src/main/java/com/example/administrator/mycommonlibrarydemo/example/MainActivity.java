package com.example.administrator.mycommonlibrarydemo.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.administrator.mycommonlibrarydemo.example.customview.CustomViewActivity;
import com.example.administrator.mycommonlibrarydemo.example.http_get_data.MainBean;
import com.example.administrator.mycommonlibrarydemo.example.http_get_data.MainHttp;
import com.example.administrator.mycommonlibrarydemo.example.http_get_data.TipsCallback;
import com.example.administrator.mycommonlibrarydemo.util.CountDownTimerUtils;

public class MainActivity extends AppCompatActivity {

    private Button mBtnGetData;
    private TextView mTvTip;
    private Button mToCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnGetData = findViewById(R.id.btn_get_data);
        mTvTip = findViewById(R.id.tv_tip);
        mToCustomView = findViewById(R.id.btn_to_customview_activity);
        
        setListener();
        
        CountDownTimerUtils countDownTimerUtils = null;
        countDownTimerUtils = CountDownTimerUtils.getCountDownTimer();
        Log.e("print", "onCreate: "+countDownTimerUtils );
        countDownTimerUtils.start();
        countDownTimerUtils.cancel();
        countDownTimerUtils = CountDownTimerUtils.getCountDownTimer();
        Log.e("print", "onCreate: "+ countDownTimerUtils);
        countDownTimerUtils.start();
        
    }

    private void setListener() {
        //点击加载数据
        mBtnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainHttp.get().cooperateApply(1, new TipsCallback<MainBean>() {
                    @Override
                    public void onSuccess(MainBean mainBean) {
                        Log.e("print", "onSuccess: "+mainBean.toString() );
                        mTvTip.setText("加载成功");
                    }

                    @Override
                    public void onFailure(String message, Throwable tr) {
                        Log.e("print", "onFailure ");
                        mTvTip.setText("加载失败");
                    }
                });
            }
        });
        
        //跳转到自定义view的使用例子
        mToCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
            }
        });
    }
}
