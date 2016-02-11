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
import com.sis.mcode.sisapp.entity.ProcAfi;
import com.sis.mcode.sisapp.entity.ProcAte;
import com.sis.mcode.sisapp.entity.ReqAfi;
import com.sis.mcode.sisapp.entity.ReqAte;
import com.sis.mcode.sisapp.entity.TipAfi;
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
    private Dao<TipAfi, Integer> tipAfiDao;

    private Dao<ProcAfi, Integer> procAfiDao;
    private Dao<ProcAte, Integer> procAteDao;
    private Dao<ReqAfi, Integer> reqAfiDao;
    private Dao<ReqAte, Integer> reqAteDao;

    private Dao<MenuItems, Integer> menuDao;
    private Dao<Config, Integer> configDao;
    private Dao<Eess,Integer> eessDao;


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
    public Dao<TipAfi, Integer> getTipAfiDao() throws SQLException{
        if(tipAfiDao == null){
            tipAfiDao = getDao(TipAfi.class);
        }
        return tipAfiDao;
    }

    public Dao<ProcAfi, Integer> getProcAfiDao() throws SQLException {
        if (procAfiDao == null) {
            procAfiDao = getDao(ProcAfi.class);
        }
        return procAfiDao;
    }

    public Dao<ProcAte, Integer> getProcAteDao() throws SQLException {
        if (procAteDao == null) {
            procAteDao = getDao(ProcAte.class);
        }
        return procAteDao;
    }

    public Dao<ReqAfi, Integer> getReqAfiDao() throws SQLException {
        if (reqAfiDao == null) {
            reqAfiDao = getDao(ReqAfi.class);
        }
        return reqAfiDao;
    }

    public Dao<ReqAte, Integer> getReqAteDao() throws SQLException {
        if (reqAteDao == null) {
            reqAteDao = getDao(ReqAte.class);
        }
        return reqAteDao;
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

    public Dao<Eess, Integer> getEessDao() throws SQLException {
        if(eessDao == null){
            eessDao = getDao(Eess.class);
        }
        return eessDao;
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