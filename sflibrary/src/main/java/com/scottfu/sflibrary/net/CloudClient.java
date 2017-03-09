package com.scottfu.sflibrary.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fujindong on 2017/3/6.
 * 网络访问
 */

public class CloudClient {
    public static void doHttpRequest(Context context, String url, final JSONResultHandler jsonResultHandler){
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


    public static void doHttpRequestV2(Context context, String url, final JSONObject jsonObject, final JSONResultHandler jsonResultHandler) {
        String sss = jsonObject.toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    jsonResultHandler.onSuccess(response.toString());
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

    public static void doHttpRequestv3(final HashMap<String,String> params, Context context, String url, final JSONResultHandler jsonResultHandler) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonResultHandler.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonResultHandler.onError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);

    }

}
