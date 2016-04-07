package com.sis.mcode.sisapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.sis.mcode.sisapp.entity.App;
import com.sis.mcode.sisapp.entity.Config;
import com.sis.mcode.sisapp.entity.Eess;
import com.sis.mcode.sisapp.entity.MenuItems;
import com.sis.mcode.sisapp.entity.SliderProcAfi;
import com.sis.mcode.sisapp.entity.SliderProcAte;
import com.sis.mcode.sisapp.entity.SliderTipSeg;
import com.sis.mcode.sisapp.entity.TipSeg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Consultor_ogti_27 on 28/12/2015.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "sisapp.db";

    private static final int DATABASE_VERSION = 1;
    private Dao<App,Integer> appDao;
    private Dao<TipSeg, Integer> tipSegDao;
    private Dao<SliderTipSeg,Integer> sliderTipSegDao;
    private Dao<SliderProcAfi,Integer> sliderProcAfiDao;
    private Dao<SliderProcAte,Integer> sliderProcAteDao;

    private Dao<MenuItems, Integer> menuDao;
    private Dao<Config, Integer> configDao;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
        try{


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int arg2, int arg3) {
        onCreate(db, connectionSource);
    }

    public Dao<App, Integer> getApp() throws SQLException{
        if(appDao == null){
            appDao = getDao(App.class);
        }
        return appDao;
    }

    public Dao<TipSeg, Integer> getTipSegDao() throws SQLException{
        if(tipSegDao == null){
            tipSegDao = getDao(TipSeg.class);
        }
        return tipSegDao;
    }

    public Dao<SliderTipSeg, Integer> getSliderTipSegDao() throws SQLException{
        if(sliderTipSegDao == null){
            sliderTipSegDao = getDao(SliderTipSeg.class);
        }
        return sliderTipSegDao;
    }

    public Dao<SliderProcAfi, Integer> getSliderProcAfiDao() throws SQLException{
        if(sliderProcAfiDao == null){
            sliderProcAfiDao = getDao(SliderProcAfi.class);
        }
        return sliderProcAfiDao;
    }

    public Dao<SliderProcAte, Integer> getSliderProcAteDao() throws SQLException{
        if(sliderProcAteDao == null){
            sliderProcAteDao = getDao(SliderProcAte.class);
        }
        return sliderProcAteDao;
    }


    public Dao<MenuItems, Integer> getMenuDao() throws SQLException{
        if(menuDao == null){
            menuDao = getDao(MenuItems.class);
        }
        return menuDao;
    }

    public Dao<Config, Integer> getConfig() throws SQLException {
        if(configDao == null){
            configDao = getDao(Config.class);
        }
        return configDao;
    }

    @Override
    public void close() {
        super.close();
    }

    public static void copyDataBase(Context context) throws IOException {
        String path = context.getFilesDir().getAbsolutePath().replace("files", "databases");
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdir();
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = path+File.separator + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public static boolean checkDataBase(Context context){
        boolean checkdb = false;
        try{
            String myPath = context.getFilesDir().getAbsolutePath().replace("files", "databases")+File.separator + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        }
        catch(SQLiteException e){
            Log.d("SisApp","Database doesn't exist");
        }

        return checkdb;
    }

}