package com.cmcm.wxnovel.reader.http;

import com.cmcm.wxnovel.reader.base.App;
import com.cmcm.wxnovel.reader.util.DeviceUtil;
import com.cmcm.wxnovel.reader.util.PKGUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by bob zhou on 2017/10/16.
 */
public class HttpHelper {

    private static HttpHelper sInstance;

    private OkHttpClient httpClient;

    private Request.Builder requsetBuilder;

    private HttpHelper() {
        httpClient = new OkHttpClient();
    }

    public static HttpHelper getInstance() {
        if (sInstance == null) {
            synchronized (HttpHelper.class) {
                if (sInstance == null) {
                    sInstance = new HttpHelper();
                }
            }
        }
        return sInstance;
    }

    public void post(String url, HashMap<String, String> data, String tag, Callback callback) {
        RequestBody body = getFormBody(data);
        Request request = getRequestBuilder()
                .tag(tag)
                .url(url)
                .post(body)
                .build();

        httpClient.newCall(request).enqueue(callback);
    }

    public void get(String url, String tag, Callback callback) {
        Request request = getRequestBuilder()
                .tag(tag)
                .url(url)
                .build();
        httpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步post
     */
    public String postSync(String url, HashMap<String, String> data, String tag) throws IOException {
        RequestBody body = getFormBody(data);
        Request request = getRequestBuilder()
                .tag(tag)
                .url(url)
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();
        return response.toString();
    }

    /**
     * 同步get
     */
    public String getSync(String url, String tag) throws IOException {
        Request request = getRequestBuilder()
                .tag(tag)
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.toString();
    }


    private Request.Builder getRequestBuilder() {
        if (requsetBuilder == null) {
            requsetBuilder = new Request.Builder()
                    .header("platform", "android")
                    .header("ver", PKGUtil.getVersionName(App.getAppContext()))
                    .header("osver", DeviceUtil.getOSVersion())
                    .header("sdk", DeviceUtil.getSDK_INT() + "")
                    .header("aid", DeviceUtil.getAID(App.getAppContext()))
                    .header("mcc", DeviceUtil.getMCC(App.getAppContext()));
        }
        return requsetBuilder;
    }

    private RequestBody getFormBody(HashMap<String, String> data) {
        FormBody.Builder builder = new FormBody.Builder();
        if (data == null || data.keySet().size() == 0) {
            builder.add("data", "nodata");
        } else {
            for (String key : data.keySet()) {
                builder.add(key, data.get(key));
            }
        }
        return builder.build();
    }

}
