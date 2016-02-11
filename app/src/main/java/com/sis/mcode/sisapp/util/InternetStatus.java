package com.sis.mcode.sisapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sis.mcode.sisapp.application.SisApp;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Consultor_ogti_27 on 28/12/2015.
 */
public class InternetStatus {
    public static boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) SisApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }else{
            return false;
        }
    }
}
