package com.sis.mcode.sisapp.resources;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sis.mcode.sisapp.R;
/**
 * Created by Consultor_ogti_27 on 28/12/2015.
 */
public class AppProperties {
    public static String getProperty(Context ctx, String key){
        InputStream is = ctx.getResources().openRawResource(R.raw.sisapp);
        Properties properties = new Properties();
        try {
            properties.load(is);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
