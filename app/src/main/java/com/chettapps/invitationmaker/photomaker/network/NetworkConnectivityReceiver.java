package com.chettapps.invitationmaker.photomaker.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.chettapps.invitationmaker.photomaker.InvitationApplication;


/* loaded from: classes2.dex */
public class NetworkConnectivityReceiver extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;

    /* loaded from: classes2.dex */
    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean z);
    }

    public static boolean isConnected() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) InvitationApplication.getInstance().getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        ConnectivityReceiverListener connectivityReceiverListener2 = connectivityReceiverListener;
        if (connectivityReceiverListener2 != null) {
            connectivityReceiverListener2.onNetworkConnectionChanged(z);
        }
    }
}
