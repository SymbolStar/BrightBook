package com.scottfu.sflibrary.net;

import com.android.volley.VolleyError;

/**
 * Created by fujindong on 2017/3/6.
 *
 * net request result JSON
 */

public abstract class JSONResultHandler {
    public abstract void onSuccess(String jsonString);

    public abstract void onError(VolleyError errorMessage);
}
