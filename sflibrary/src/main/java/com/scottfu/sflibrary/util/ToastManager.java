package com.scottfu.sflibrary.util;

/**
 * Created by fujindong on 2017/3/7.
 */

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * 获取UI线程 显示Toast 避免子线程中调用Toast出现的问题
 */
public class ToastManager {
    private static Toast toast;


    public static void showToast(final Context context, final CharSequence text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void showToast(final Context context, final int values) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,String.valueOf(values),Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 频繁显示的提示
     * @param context
     * @param text
     */
    public static void repeatToast(final Context context, final CharSequence text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (null == toast) {
                    toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {
                    toast.setText(text);
                }
                toast.show();
            }
        });
    }

}
