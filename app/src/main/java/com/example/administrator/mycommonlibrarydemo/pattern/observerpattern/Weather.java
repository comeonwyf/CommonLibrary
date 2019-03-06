package com.example.administrator.mycommonlibrarydemo.pattern.observerpattern;

/**
 * Created by wuyufeng    on  2019/3/6 0006.
 * interface by
 */

public class Weather {
    public String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Weather{" + "des='" + des + '\'' + '}';
    }
}
