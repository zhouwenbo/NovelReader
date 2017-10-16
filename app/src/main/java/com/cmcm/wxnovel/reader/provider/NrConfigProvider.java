package com.cmcm.wxnovel.reader.provider;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.cmcm.wxnovel.reader.base.App;
import com.cmcm.wxnovel.reader.util.ConfigManager;

public class NrConfigProvider extends ContentProvider {

    public static final Uri CONFIG_CONTENT_URI = Uri.parse("content://com.cmcm.wxnovel.reader.provider.config");

    private static final int LENGTH_CONTENT_URI = CONFIG_CONTENT_URI.toString().length() + 1;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }


    private static String EXTRA_TYPE = "type";
    private static String EXTRA_KEY = "key";
    private static String EXTRA_VALUE = "value";

    private static final int TYPE_BOOLEAN = 1;
    private static final int TYPE_INT = 2;
    private static final int TYPE_LONG = 3;
    private static final int TYPE_STRING = 4;
    private static final int TYPE_FLOAT = 5;


    private static Context context;

    public static void setAppContext(Context context) {
        if (NrConfigProvider.context == null) {
            NrConfigProvider.context = context;
        }
    }

    private static ContentResolver getCr() {
        Context context = NrConfigProvider.context != null ? NrConfigProvider.context : App.getAppContext();
        return context.getContentResolver();
    }

    private static boolean s_bFixedSysBug = false;
    private static Object s_LockFixedBug = new Object();
    private static ContentProviderClient s_cpClientFixer = null;

    private static void FixProviderSystemBug() {
        synchronized (s_LockFixedBug) {
            if (s_bFixedSysBug)
                return;
            s_bFixedSysBug = true;

            if ((Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 18)
                    || (Build.VERSION.SDK_INT >= 9 && Build.VERSION.SDK_INT <= 10)) {
                s_cpClientFixer = getCr().acquireContentProviderClient(CONFIG_CONTENT_URI);
            }
        }
    }

    public static boolean contains(String key) {
        FixProviderSystemBug();

        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_LONG);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, 0);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, values);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalStateException e) {
            return false;
        }

        if (result == null) {
            return false;
        }

        return true;
    }

    public static void setBooleanValue(String key, boolean value) {
        ContentValues values = new ContentValues();

        values.put(EXTRA_TYPE, TYPE_BOOLEAN);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, values, null, null);
        } catch (IllegalArgumentException e) {
            // FIX CITS#183
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void setLongValue(String key, long value) {
        ContentValues values = new ContentValues();

        values.put(EXTRA_TYPE, TYPE_LONG);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, values, null, null);
        } catch (IllegalArgumentException e) {
            // FIX CITS#183
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void setIntValue(String key, int value) {
        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_INT);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, values, null, null);
        } catch (IllegalArgumentException e) {
            // FIX CITS#183
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void setFloatValue(String key, float value) {
        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_FLOAT);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, values, null, null);
        } catch (IllegalArgumentException e) {
            // FIX CITS#183
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void setStringValue(String key, String value) {
        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_STRING);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, value);

        FixProviderSystemBug();
        try {
            getCr().update(CONFIG_CONTENT_URI, values, null, null);
        } catch (IllegalArgumentException e) {
            // FIX CITS#183
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    public static long getLongValue(String key, long defValue) {
        FixProviderSystemBug();

        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_LONG);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, values);
        } catch (IllegalArgumentException e) {
            return defValue;
        } catch (IllegalStateException e) {
            return defValue;
        }

        if (result == null) {
            return defValue;
        } else {
            String str = result.toString();
            if (TextUtils.isEmpty(str)) {
                return defValue;
            } else if (str.length() <= LENGTH_CONTENT_URI) {
                return defValue;
            }
        }

        return Long.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }

    public static boolean getBooleanValue(String key, boolean defValue) {
        FixProviderSystemBug();

        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_BOOLEAN);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, values);
        } catch (IllegalArgumentException e) {
            return defValue;
        } catch (IllegalStateException e) {
            return defValue;
        }

        if (result == null)
            return defValue;

        return Boolean.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }

    public static int getIntValue(String key, int defValue) {
        FixProviderSystemBug();

        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_INT);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, values);
        } catch (IllegalArgumentException e) {
            return defValue;
        } catch (IllegalStateException e) {
            return defValue;
        }

        if (result == null)
            return defValue;

        return Integer.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }

    public static float getFloatValue(String key, float defValue) {
        FixProviderSystemBug();

        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_FLOAT);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, values);
        } catch (IllegalArgumentException e) {
            return defValue;
        } catch (IllegalStateException e) {
            return defValue;
        }

        if (result == null)
            return defValue;

        return Float.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }

    public static String getStringValue(String key, String defValue) {
        FixProviderSystemBug();

        ContentValues values = new ContentValues();
        values.put(EXTRA_TYPE, TYPE_STRING);
        values.put(EXTRA_KEY, key);
        values.put(EXTRA_VALUE, defValue);
        Uri result = null;

        try {
            result = getCr().insert(CONFIG_CONTENT_URI, values);
        } catch (IllegalArgumentException e) {
            return defValue;
        } catch (IllegalStateException e) {
            return defValue;
        }

        if (result == null)
            return defValue;

        return String.valueOf(result.toString().substring(LENGTH_CONTENT_URI));
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String res = "";
        int nType = values.getAsInteger(EXTRA_TYPE);
        if (nType == TYPE_BOOLEAN) {
            res += ConfigManager.getInstance().getBooleanValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsBoolean(EXTRA_VALUE));
        } else if (nType == TYPE_STRING) {
            res += ConfigManager.getInstance().getStringValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsString(EXTRA_VALUE));
        } else if (nType == TYPE_INT) {
            res += ConfigManager.getInstance().getIntValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsInteger(EXTRA_VALUE));
        } else if (nType == TYPE_LONG) {
            res += ConfigManager.getInstance().getLongValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsLong(EXTRA_VALUE));
        } else if (nType == TYPE_FLOAT) {
            res += ConfigManager.getInstance().getFloatValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsFloat(EXTRA_VALUE));
        }
        return Uri.parse(CONFIG_CONTENT_URI.toString() + "/" + res);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int nType = values.getAsInteger(EXTRA_TYPE);
        if (nType == TYPE_BOOLEAN) {
            ConfigManager.getInstance().setBooleanValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsBoolean(EXTRA_VALUE));
        } else if (nType == TYPE_STRING) {
            ConfigManager.getInstance().setStringValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsString(EXTRA_VALUE));
        } else if (nType == TYPE_INT) {
            ConfigManager.getInstance().setIntValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsInteger(EXTRA_VALUE));
        } else if (nType == TYPE_LONG) {
            ConfigManager.getInstance().setLongValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsLong(EXTRA_VALUE));
        } else if (nType == TYPE_FLOAT) {
            ConfigManager.getInstance().setFloatValue(
                    values.getAsString(EXTRA_KEY),
                    values.getAsFloat(EXTRA_VALUE));
        }
        return 1;
    }

}
