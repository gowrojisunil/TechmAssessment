package com.example.sampleapp.helper;
/**
 * Created by sunil gowroji on 07/07/15.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networkstatus {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = Networkstatus.getConnectivityStatus(context);
        String status = null;
        if (conn == Networkstatus.TYPE_WIFI) {
            status = "Wifi is detected";
        } else if (conn == Networkstatus.TYPE_MOBILE) {
            status = "Mobile data connection is detected";
        } else if (conn == Networkstatus.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet connection";
        }
        return status;
    }
}
