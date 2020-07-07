package com.example.sampleapp.helper;
/**
 * Created by sunil gowroji on 07/07/15.
 */

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class ConnectivityDetection extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, final Intent intent) {
        String status = Networkstatus.getConnectivityStatusString(context);
        if (status.equalsIgnoreCase("Not connected to Internet connection")) {
            onNetworkChange(status);
        }
    }

    protected abstract void onNetworkChange(String status);
}