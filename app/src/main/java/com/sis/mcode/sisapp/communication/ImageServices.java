package com.sis.mcode.sisapp.communication;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.sis.mcode.sisapp.service.impl.ConfigurationServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageServices {

    private ConfigurationServiceImpl cfgService = new ConfigurationServiceImpl();
    String url = String.format("http://%s/sisGestorDeContenidos/files/", cfgService.getConfiguration().getServer());

    public ImageServices() {
        cfgService = new ConfigurationServiceImpl();
    }

    public Bitmap downloadImage(String imageHttpAddress){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(url + imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return imagen;
    }

    public String saveImage(Context context, String nombre, Bitmap imagen){
        ContextWrapper cw = new ContextWrapper(context);

        File dirImages = cw.getDir("sisapp", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, nombre);

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }

}