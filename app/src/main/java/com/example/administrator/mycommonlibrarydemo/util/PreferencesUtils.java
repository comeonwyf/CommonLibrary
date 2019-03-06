package com.example.administrator.mycommonlibrarydemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Create_time: 2018/11/11 9:54
 * @Author: wr
 * @Description: ${TODO}(PreferencesUtils工具类)
 */

public class PreferencesUtils {
    private SharedPreferences mSharedPreferences;
     //保存手机里面的名字
     private SharedPreferences.Editor mEditor;

    public PreferencesUtils(Context context, String fileName) {
        this(context, fileName, Context.MODE_PRIVATE);
    }

    public PreferencesUtils(Context context, String fileName, int mode){
        mSharedPreferences = context.getSharedPreferences(fileName, mode);
        mEditor = mSharedPreferences.edit();
    }

    public void putString(String key, String value){
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public void putInt(String key, int value){
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public void putBoolean(String key, boolean value){
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public void putFloat(String key, float value){
        mEditor.putFloat(key, value);
        mEditor.apply();
    }

    public void putLong(String key, long value){
        mEditor.putLong(key, value);
        mEditor.apply();
    }

    public <T extends Serializable> void putObject(String key, T value) {
        if(value == null){
            return;
        }
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.NO_WRAP));
            putString(key, objectStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          if(oos != null){
              try {
                  oos.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
          if(baos != null){
              try {
                  baos.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }
    }

    public <T extends Serializable> void putObjectList(String key, List<T> values){
        removeList(key);
        if(values != null && values.size() > 0){
            int size = values.size();
            putInt(key + "size", size);
            for (int i = 0; i < size; ++i) {
                T value = values.get(i);
                putObject(key + "i", value);
            }
        }
    }

    public void putStringSet(String key, Set<String> values){
        mEditor.putStringSet(key, values);
        mEditor.apply();
    }

    public void putStringList(String key, List<String> values){
        removeList(key);
        if(values != null && values.size() > 0){
            int size = values.size();
            putInt(key + "size", size);
            for (int i = 0; i < size; ++i){
                String value = values.get(i);
                putString(key + i, value);
            }
        }
    }

    public String getString(String key){
        return getString(key, null);
    }

    public String getString(String key, String defValue){
        return mSharedPreferences.getString(key, defValue);
    }

    public int getInt(String key){
        return getInt(key, 0);
    }

    public int getInt(String key, int defValue){
        return mSharedPreferences.getInt(key, defValue);
    }

    public boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue){
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public float getFloat(String key){
        return getFloat(key, 0f);
    }

    public float getFloat(String key, float defValue){
        return mSharedPreferences.getFloat(key, defValue);
    }

    public long getLong(String key){
        return getLong(key, 0);
    }

    public long getLong(String key, long defValue){
        return mSharedPreferences.getLong(key, defValue);
    }

    public <T extends Serializable> T getObject(String key){
        return getObject(key, null);
    }

    public <T extends Serializable> T getObject(String key, T defValue){
        String value = getString(key, null);
        if(!TextUtils.isEmpty(value)) {
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;
            byte[] byteValue = Base64.decode(value, Base64.NO_WRAP);
            try {
                bais = new ByteArrayInputStream(byteValue);
                ois = new ObjectInputStream(bais);
                defValue = (T) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (bais != null) {
                    try {
                        bais.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return defValue;
    }

    public Set<String> getStringSet(String key){
        return getStringSet(key, null);
    }

    public Set<String> getStringSet(String key, Set<String> defValues){
        return mSharedPreferences.getStringSet(key, defValues);
    }

    public List<String> getStringList(String key){
        return getStringList(key, null);
    }

    public List<String> getStringList(String key, List<String> defValues){
        int size = mSharedPreferences.getInt(key + "size", 0);
        if(size > 0){
            defValues = new ArrayList<>();
            for (int i = 0; i < size; ++i){
                defValues.add(mSharedPreferences.getString(key + i, ""));
            }
        }
        return defValues;
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

    public void remove(String key) {
        if(mSharedPreferences.contains(key + "size")){
            removeList(key);
        } else {
            mEditor.remove(key);
            mEditor.apply();
        }
    }

    public void removeList(String key){
        int size = mSharedPreferences.getInt(key + "size", 0);
        for (int i = 0; i < size; ++i){
            mEditor.remove(key + i);
            mEditor.apply();
        }
        mEditor.remove(key + "size");
        mEditor.apply();
    }

    public void clear() {
        mEditor.clear();
        mEditor.apply();
    }

    public Boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }
}
