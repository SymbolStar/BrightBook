package com.scottfu.brightbook.interfacebk;

import com.android.volley.VolleyError;

/**
 * Created by fujindong on 2017/3/15.
 */

public interface OnStringListener {

    /**
     * 请求成功时回调
     * @param result
     */
    void onSuccess(String result);

    /**
     * 请求失败时回调
     * @param error
     */
    void onError(VolleyError error);

}