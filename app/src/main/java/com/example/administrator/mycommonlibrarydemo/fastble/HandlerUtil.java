package com.example.administrator.mycommonlibrarydemo.fastble;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/**
 * Created by wuyufeng on 2017/12/27.
 *采用软引用来创建handler，防止内存泄漏
 */

public final class HandlerUtil {
    private HandlerUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 收到消息的回调接口
     */
    public interface OnReceiveMessageListener{
        void handlerMessage(Message msg);
    }


    public static class HandlerHolder extends Handler{

        WeakReference<OnReceiveMessageListener> mListenerWeakReference;

        /**
         * 使用必读：推荐在Activity或者Activity内部持有类中实现该接口，不要使用匿名类，可能会被GC
         * @param listener 收到消息回调接口
         */
        public HandlerHolder(OnReceiveMessageListener listener){
            mListenerWeakReference = new WeakReference<>(listener);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mListenerWeakReference != null && mListenerWeakReference.get() != null) {
                mListenerWeakReference.get().handlerMessage(msg);
            }
        }

    }

    /**
     * 使用实例
     *public class MainActivity extends AppCompatActivity implements HandlerUtils.OnReceiveMessageListener{
         private HandlerUtils.HandlerHolder handlerHolder;
         private static final int M_ONE=12;
         private static final int M_TWO=14;

         @Override
         protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            handlerHolder = new HandlerUtils.HandlerHolder(this); 使用必读：推荐在Activity或者Activity内部持有类中实现该接口，不要使用匿名类

            handlerHolder.sendEmptyMessage(M_ONE);
            handlerHolder.sendEmptyMessage(M_TWO);
         }

        @Override
        public void handlerMessage(Message msg) {
          switch (msg.what){
              case M_ONE:
                   Toast.makeText(this, "M_ONE", Toast.LENGTH_SHORT).show();
              break;
               case M_TWO:
               Toast.makeText(this, "M_TWO", Toast.LENGTH_SHORT).show();
              break;
         }
       }
     }
     *
     */


}
