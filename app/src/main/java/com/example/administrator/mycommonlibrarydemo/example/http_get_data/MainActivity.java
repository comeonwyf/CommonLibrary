package com.example.administrator.mycommonlibrarydemo.example.http_get_data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.administrator.mycommonlibrarydemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainHttp.get().cooperateApply(1, new TipsCallback<MainBean>() {
            @Override
            public void onSuccess(MainBean mainBean) {
                Log.e("print", "onSuccess: "+mainBean.toString() );
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                Log.e("print", "onFailure: "+message );
            }
        });
    }
    
}
