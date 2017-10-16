package com.cmcm.wxnovel.reader.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;

import com.cmcm.wxnovel.reader.util.RuntimeCheck;

/**
 * Created by bob zhou on 2017/10/16.
 */

public class App extends MultiDexApplication {

    private static Context sAppContext;

    private static Handler sMainHandler;

    public static Context getAppContext() {
        return sAppContext;
    }

    public static Handler getMainHandler() {
        if (null == sMainHandler) {
            synchronized (App.class) {
                if (null == sMainHandler) {
                    sMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return sMainHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sAppContext = base;
        RuntimeCheck.init(base);
    }
}
