package com.cmcm.wxnovel.reader.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cmcm.wxnovel.reader.base.App;
import com.cmcm.wxnovel.reader.provider.NrConfigProvider;

/**
 * Created by bob zhou on 2017/10/16.
 */
public class ConfigManager {

    private static ConfigManager sInstance;

    private SharedPreferences sharedPreferences = null;

    private ConfigManager() {
        String name = App.getAppContext().getPackageName() + "_ui_preferences";
        sharedPreferences = App.getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static ConfigManager getInstance() {
        if (sInstance == null) {
            synchronized (ConfigManager.class) {
                if (sInstance == null) {
                    sInstance = new ConfigManager();
                }
            }
        }
        return sInstance;
    }

    private SharedPreferences getSharedPreference() {
        return sharedPreferences;
    }

    /////////////////////// base getter start /////////////////////////////////
    public boolean getBooleanValue(String key, boolean defValue) {
        if (RuntimeCheck.isUIProcess()) {
            return getSharedPreference().getBoolean(key, defValue);
        } else {
            return NrConfigProvider.getBooleanValue(key, defValue);
        }
    }

    public String getStringValue(String key, String defValue) {
        if (RuntimeCheck.isUIProcess()) {
            return getSharedPreference().getString(key, defValue);
        } else {
            return NrConfigProvider.getStringValue(key, defValue);
        }
    }

    public int getIntValue(String key, int defValue) {
        if (RuntimeCheck.isUIProcess()) {
            return getSharedPreference().getInt(key, defValue);
        } else {
            return NrConfigProvider.getIntValue(key, defValue);
        }
    }

    public long getLongValue(String key, long defValue) {
        if (RuntimeCheck.isUIProcess()) {
            return getSharedPreference().getLong(key, defValue);
        } else {
            return NrConfigProvider.getLongValue(key, defValue);
        }
    }

    public float getFloatValue(String key, float defValue) {
        if (RuntimeCheck.isUIProcess()) {
            return getSharedPreference().getFloat(key, defValue);
        } else {
            return NrConfigProvider.getFloatValue(key, defValue);
        }
    }
    /////////////////////// base getter end /////////////////////////////////


    /////////////////////// base setter start ///////////////////////////////
    public void setBooleanValue(String key, boolean value) {
        if (RuntimeCheck.isUIProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putBoolean(key, value);
            editor.apply();
        } else {
            NrConfigProvider.setBooleanValue(key, value);
        }
    }

    public void setStringValue(String key, String value) {
        if (RuntimeCheck.isUIProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putString(key, value);
            editor.apply();
        } else {
            NrConfigProvider.setStringValue(key, value);
        }
    }

    public void setIntValue(String key, int value) {
        if (RuntimeCheck.isUIProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putInt(key, value);
            editor.apply();
        } else {
            NrConfigProvider.setIntValue(key, value);
        }
    }

    public void setLongValue(String key, long value) {
        if (RuntimeCheck.isUIProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putLong(key, value);
            editor.apply();
        } else {
            NrConfigProvider.setLongValue(key, value);
        }
    }

    public void setFloatValue(String key, float value) {
        if (RuntimeCheck.isUIProcess()) {
            SharedPreferences.Editor editor = getSharedPreference().edit();
            editor.putFloat(key, value);
            editor.apply();
        } else {
            NrConfigProvider.setFloatValue(key, value);
        }
    }
    /////////////////////// base setter end /////////////////////////////////



}
