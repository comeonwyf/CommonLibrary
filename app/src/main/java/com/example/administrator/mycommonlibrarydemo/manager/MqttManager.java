package com.example.administrator.mycommonlibrarydemo.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.gson.Gson;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttService;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by wuyufeng on 2018/12/2.
 * 采用EventBus来传递接收的消息
 */
public class MqttManager {
    public static final String TAG = MqttManager.class.getSimpleName();
    private Context context;
    private MqttAndroidClient client;

    private static final boolean AUTO_RECONNECT = true; // Broker Port
    private static final boolean CLEAN_SESSION = true;
    public static final int QOS = 0;
    
    private String SERVER_URL = "请填写URL";
    
    private String USER_NAME = "MozhenqiangAdmin"; //可不填  视服务器配置而定
    private String USER_PASSWORD = "MozhenqiangAdmin"; //可不填  视服务器配置而定
   
    private   int WHAT_RECONNECT = 1;

    //public static  String SERVER_URL = "tcp://47.101.158.248";//mqtt服务器地址和端口
    //public static  String USER_NAME = "dlc";//可不填  视服务器配置而定
    //public static  String USER_PASSWORD = "123456";//可不填  视服务器配置而定
    //public static  String CLIENT_ID = "我是测试id,111";//客户端标识

    private static MqttManager mqttManager = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_RECONNECT) {//重新连接
                //connect();
            }else if(msg.what == 2){
                //ToastUtil.showOne(context,"MQTT连接成功！");
            }
        }
    };
    private Gson mGson;
    private MqttConnectOptions mMqttConnectOptions;
    private DisconnectedBufferOptions mDisconnectedBufferOptions;
    private String mCLIENT_id;
    private String mTOPIC_driver_id;
 

    public static MqttManager getInstance(Context context) {
        if (mqttManager == null) {
            mqttManager = new MqttManager(context);
        }
        return mqttManager;
    }

    private MqttManager(Context context) {
        this.context = context;

        //mPreferencesUtils = GlobalPreferences.getInstance(context).getPreferencesUtils();
        //if(App.getBaseInfo()==null){
        //    App.setBaseInfo((BaseInfo) mPreferencesUtils.getObject(Constant.LOGIN_INFO));
        //}
        //mCLIENT_id = "com.ruyishangwu.driverapp"+ App.getBaseInfo().getMemberId();
        //mTOPIC_driver_id = App.getBaseInfo().getMemberId();
        
        client = new MqttAndroidClient(context, SERVER_URL, mCLIENT_id);
        mGson = new Gson();

        mMqttConnectOptions = new MqttConnectOptions();
        mMqttConnectOptions.setAutomaticReconnect(true);
        mMqttConnectOptions.setCleanSession(CLEAN_SESSION);//清理缓存
        mMqttConnectOptions.setConnectionTimeout(20); // 设置超时时间，单位：秒
        mMqttConnectOptions.setKeepAliveInterval(10); // 设置超时时间，单位：秒
        mMqttConnectOptions.setUserName(USER_NAME); // 用户名
        mMqttConnectOptions.setPassword(USER_PASSWORD.toCharArray()); //密码,将字符串转换为字符串数组
        mMqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);

        mDisconnectedBufferOptions = new DisconnectedBufferOptions();
        mDisconnectedBufferOptions.setBufferEnabled(true);
        mDisconnectedBufferOptions.setBufferSize(100);
        mDisconnectedBufferOptions.setPersistBuffer(false);
        mDisconnectedBufferOptions.setDeleteOldestMessages(false);
    }

    /**
     * 启动mqtt
     */
    public void startMqtt() {
        startService();
        setCallback();
        connect();
    }

    /**
     * 启动关闭mqtt
     */
    public void stopMqtt() {
        stopService();
        disconnect();
    }

    /**
     * 发送消息给服务器
     *
     * @param publishMessage 消息内容
     */
    public void publishMessage(String topic, String publishMessage) {
        if (client == null || !client.isConnected()) {
            return;
        }
        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(publishMessage.getBytes());
            client.publish(topic, message);
            Log.e(TAG, "Mqtt 发送消息：" + publishMessage);
            if (!client.isConnected()) {
                Log.e(TAG, client.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (MqttException e) {
            Log.e(TAG, "Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isConnectedSuccess(){
        if(client!=null && client.isConnected()){
            return true;
        }else {
            return false; 
        }
    }

    private void setCallback() {
        if (client == null) {
            return;
        }
        
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURL) {
                if (reconnect) {
                    Log.e(TAG, "connectComplete: " + serverURL);
                    // Because Clean Session is true, we need to re-subscribe
                    subscribe();
                } else {
                    Log.e(TAG, "Connected to: " + serverURL);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "The Connection was lost.");
                
                if(mHandler!=null) {
                    //mHandler.postDelayed(new Runnable() {
                    //    @Override
                    //    public void run() {
                    //        connect();
                    //    }
                    //},5000);
                    mHandler.sendEmptyMessageDelayed(WHAT_RECONNECT, 5000);
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String content = new String(message.getPayload());
                Log.e(TAG, message.getId()+"-->receive message: " + content);
                if(mGson == null){
                    mGson = new Gson();
                }
                //MqttMessageEventBusBean mqttMessageEventBusBean = mGson.fromJson(content,MqttMessageEventBusBean.class);
                //EventBus.getDefault().post(mqttMessageEventBusBean);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {
                    Log.e(TAG, "deliveryComplete:" + token != null ? token.getMessage().toString() : "");
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private IMqttActionListener mIMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            Log.e(TAG, "connect onSuccess:" + SERVER_URL);
            client.setBufferOpts(mDisconnectedBufferOptions);
            subscribe();
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            Log.e(TAG, "Failed to connect to:" + SERVER_URL);
            if(mHandler!=null) {
                //mHandler.postDelayed(new Runnable() {
                //    @Override
                //    public void run() {
                //        try {
                //            client.disconnect();
                //        } catch (MqttException e) {
                //            e.printStackTrace();
                //        }
                //        connect();
                //    }
                //},10000);
                mHandler.sendEmptyMessageDelayed(WHAT_RECONNECT, 5000);
            }
        }
    };

    public void connect() {
        if (client == null) {
            return;
        }
        try {
            if(client.isConnected()){
                client.disconnect();
            }
            client.connect(mMqttConnectOptions, null, mIMqttActionListener);
        } catch (MqttException ex) {
            Log.e(TAG, "MqttException ex:" + ex);
            ex.printStackTrace();
        }
    }

    private void subscribe() {
        if (client == null) {
            return;
        }
        try {
            if(client!=null){
                client.subscribe(mTOPIC_driver_id, QOS, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.e(TAG, "Subscribed success");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.e(TAG, "Failed to subscribe");
                    }
                });
            }
        } catch (MqttException ex) {
            Log.e(TAG, "Exception whilst subscribing");
            ex.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void disconnect() {
        try {
            if (mHandler != null) {
                mHandler.removeMessages(WHAT_RECONNECT);
                mHandler = null;
            }
            if (client != null) {
                client.disconnect();
                client.unregisterResources();
                //client.close();
                mqttManager = null;
                client = null;
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void stopService() {
        if (isServiceRunning(MqttService.class)) {
            context.stopService(new Intent(context, MqttService.class));
        }
    }

    private void startService() {
        if (!isServiceRunning(MqttService.class)) {
            context.startService(new Intent(context, MqttService.class));
        }
    }

    
}
