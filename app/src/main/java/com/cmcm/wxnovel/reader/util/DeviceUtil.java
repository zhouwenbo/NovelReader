package com.cmcm.wxnovel.reader.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by bob zhou on 2017/10/16.
 */
public class DeviceUtil {

    public static String getAID(Context context) {
        String androidID = "";
        try {
            androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return androidID;
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static int getSDK_INT() {
        return Build.VERSION.SDK_INT;
    }

    public static String getMCC(Context context) {
        if (context == null) {
            return "";
        }
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mcc_mnc = tm.getSimOperator();
        StringBuilder mcc = null;
        if (null != mcc_mnc && mcc_mnc.length() >= 3) {
            mcc = new StringBuilder();
            mcc.append(mcc_mnc, 0, 3);
            return mcc.toString();
        }
        return "";
    }

}
