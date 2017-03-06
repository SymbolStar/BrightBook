package com.scottfu.sflibrary.net;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by fujindong on 2017/3/6.
 * 网络访问
 */

public class CloudClient {
    private Context context;
    public CloudClient(Context context){
        this.context = context;
    }
    public void doHttpRequest(String url, final JSONResultHandler jsonResultHandler){
        StringRequest request = new StringRequest(url, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                jsonResultHandler.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonResultHandler.onError(error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }
}
