package com.example.administrator.mycommonlibrarydemo.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.administrator.mycommonlibrarydemo.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RXjave2DemoActivity extends AppCompatActivity {

    private EditText mEtContent;
    private TextView mTvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjave2_demo);

        mEtContent = findViewById(R.id.et);
        mTvSearch = findViewById(R.id.tv_search);

        Observable<String> observable = createObservable();
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
            
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("print", "accept: " +s);
            }
        });
        Log.e("print", "onCreate: "+getPackageName() );
    }
    
    private Observable<String> createObservable(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                mTvSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("print", "onClick: " );
                        emitter.onNext(mEtContent.getText().toString());
                    }
                });
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        Log.e("print", "cancel: " );
                        mTvSearch.setOnClickListener(null);
                    }
                });
            }
        });
    }
    
}
