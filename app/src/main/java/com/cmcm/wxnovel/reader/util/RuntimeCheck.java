package com.cmcm.wxnovel.reader.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by bob zhou on 2017/10/16.
 */


public class RuntimeCheck {

    private static String SERVICE_NAME = ":service";

    private static boolean isServiceProcess = false;

    private static boolean isUiProcess = false;


    public static void init(Context context) {
        init(getProcessName(context));
    }

    private static void init(String processName) {
        if (processName.contains(SERVICE_NAME)) {
            isServiceProcess = true;
        } else {
            isUiProcess = true;
        }
    }

    public static boolean isServiceProcess() {
        return isServiceProcess;
    }

    public static boolean isUIProcess() {
        return isUiProcess;
    }


    public static String getProcessName(Context base) {
        File cmdFile = new File("/proc/self/cmdline");

        if (cmdFile.exists() && !cmdFile.isDirectory()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(cmdFile)));
                String procName = reader.readLine();

                if (!TextUtils.isEmpty(procName))
                    return procName.trim();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //try to fix SELinux limit due to unable access /proc file system
            ActivityManager am = (ActivityManager) base.getSystemService(Context.ACTIVITY_SERVICE);
            if (null != am) {
                List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = am.getRunningAppProcesses();
                if (null != appProcessInfoList) {
                    for (ActivityManager.RunningAppProcessInfo i : appProcessInfoList) {
                        if (i.pid == android.os.Process.myPid()) {
                            return i.processName.trim();
                        }
                    }
                }
            }
        }

        //Warnning: getApplicationInfo().processName only return package name for some reason, you will not see
        // the real process name, such as com.cleanmaster.mguard:service
        return base.getApplicationInfo().processName;
    }

}
