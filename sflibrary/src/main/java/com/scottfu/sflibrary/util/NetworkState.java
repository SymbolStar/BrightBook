package com.scottfu.sflibrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by fujindong on 2017/3/6.
 * 网络状态
 */

public class NetworkState {
    /**
     * 检查网络是否连接
     * @param context
     * @return
     */
    public static boolean networkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    /**
     *检查WiFi是否连接
     * @param context
     * @return
     */
    public static boolean wifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                 //TODO 是否可以用这个方法来判断连接  简化代码    info.getType();
                    return info.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 检查移动网络是否连接
     * @param context
     * @return
     */
    public static boolean mobileDataConntected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE);
                return info.isAvailable();
            }
        }
        return false;
    }


}
