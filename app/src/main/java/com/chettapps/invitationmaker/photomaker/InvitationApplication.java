package com.chettapps.invitationmaker.photomaker;

import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Base64;

import androidx.multidex.MultiDexApplication;


import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.pojoClass.Advertise;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class InvitationApplication extends MultiDexApplication {




    private static InvitationApplication mInstance;
    private String FULL_URL_MAIN_BACKUP = "";
    private RequestQueue mRequestQueue;
    static InvitationApplication invitationApplication;
    public static InvitationApplication getInstance() {

        synchronized (InvitationApplication.class) {
            synchronized (InvitationApplication.class) {
                synchronized (InvitationApplication.class) {
                    invitationApplication = mInstance;
                }
                return invitationApplication;
            }
        }

    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String str) {
        if (TextUtils.isEmpty(str)) {

        }
        request.setTag(str);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {

        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object obj) {
        RequestQueue requestQueue = this.mRequestQueue;
        if (requestQueue != null) {
            requestQueue.cancelAll(obj);
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        mInstance = this;




    }
}
