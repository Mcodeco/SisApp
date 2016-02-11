package com.sis.mcode.sisapp.application;

import android.app.Application;
import android.content.Context;

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

    public static Context getAppContext()
    {
        return SisApp.context;
    }
}
