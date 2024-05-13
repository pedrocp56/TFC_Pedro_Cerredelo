package com.example.app;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ApiRequest {

    private final Context mContext;
    private final RequestQueue mRequestQueue;
    private static ApiRequest mInstance;

    private ApiRequest(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized ApiRequest getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ApiRequest(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            return Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public void performRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        addToRequestQueue(stringRequest);
    }
}
