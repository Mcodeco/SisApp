package com.sis.mcode.sisapp.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Consultor_ogti_27 on 28/12/2015.
 */
public class SisApp extends Application{

    private static Context context;

    public void onCreate()
    {
        super.onCreate();
        SisApp.context = getApplicationContext();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext()
    {
        return SisApp.context;
    }
}
