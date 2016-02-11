package com.sis.mcode.sisapp.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Consultor_ogti_27 on 28/12/2015.
 */
public class SAppUtil {

    public static int getAppVersionCode(Context context) {
        int version = -1;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }

        return version;
    }
}
