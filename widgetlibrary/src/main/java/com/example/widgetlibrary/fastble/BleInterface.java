package com.example.widgetlibrary.fastble;

/**
 * Created by wuyufeng    on  2018/11/1 0001.
 * 指令
 */

public interface BleInterface {
    public String UUID_WRITE_SERVICE = "0000ffe5-0000-1000-8000-00805f9b34fb";
    public String UUID_WRITE = "0000ffe9-0000-1000-8000-00805f9b34fb";

    public String UUID_NOTIFY_SERVICE = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public String UUID_NOTIFY = "0000ffe4-0000-1000-8000-00805f9b34fb";
    
    //温度相关指令
    public String TEMPERATURE_OPEN = "885A0601%s%s0000000055AA0B";//打开; %s 温度 与 输出时间（小时1-8）
    public String TEMPERATURE_CLOSE = "885A0600%s%s0000000055AA0B";//关; %s 温度 与 输出时间（小时1-8）
    public String TEMPERATURE_QUERY = "885A070000000000000055AA0B";//温度查询
    
    //律动相关指令
    public String SHAKE_OPEN = "885A0501%s%s0000000055AA0B";//开; %s PWM 与 时间 （6挡 --86  117 150 183 216 255）
    public String SHAKE_CLOSE = "885A050000000000000055AA0B";//关;
    
    //头部相关指令
    public String HEADER_UP = "885A010102000000000055AA0B";//升
    public String HEADER_DOWN = "885A020102000000000055AA0B";//降;

    //脚部相关指令
    public String FOOTER_UP = "885A030102000000000055AA0B";//升
    public String FOOTER_DOWN = "885A040102000000000055AA0B";//降;

    //头脚部相关指令
    public String HEADER_FOOTER_UP = "885A130102000000000055AA0B";//升
    public String HEADER_FOOTER_DOWN = "885A240102000000000055AA0B";//降;
    
    public int SHAKE_LEVEL1 = 86;
    public int SHAKE_LEVEL2 = 117;
    public int SHAKE_LEVEL3 = 150;
    public int SHAKE_LEVEL4 = 183;
    public int SHAKE_LEVEL5 = 216;
    public int SHAKE_LEVEL6 = 255;
    
    
}
