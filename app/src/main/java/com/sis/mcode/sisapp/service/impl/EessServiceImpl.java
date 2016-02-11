package com.sis.mcode.sisapp.service.impl;

import android.content.Context;
import android.util.Log;

//import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;
import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.communication.DownloadListOfEessResult;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.entity.Eess;

import java.sql.SQLException;
import java.util.List;

import roboguice.inject.ContextSingleton;

/**
 * Created by Consultor_ogti_27 on 28/01/2016.
 */
@ContextSingleton
public class EessServiceImpl {

    private DBHelper dbhelper;

    @Inject
    private Context context;


    public List<Eess> getEessList() {
        try {
            Dao<Eess, Integer> dao = getHelper().getEessDao();
            List<Eess> items = dao.queryForAll();
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DBHelper getHelper() {
        if (this.dbhelper == null) {
            this.dbhelper = OpenHelperManager.getHelper(this.context,
                    DBHelper.class);
        }
        return this.dbhelper;
    }

    public DownloadListOfEessResult downloadEess(LatLng ll){
        SoapServices service = new SoapServices(this.context);
        DownloadListOfEessResult result = service.getEess(ll);


        if(result.isSuccess()){
            try {
                if(result.getData().size() > 0){
                    Dao<Eess, Integer> asDao = getHelper().getEessDao();

                    for(Eess as : result.getData()){
                        asDao.createIfNotExists(as);
                    }
                } else {
                    result.setErrorMessage("No hay Establecimientos de Salud cercanos.");
                    result.setUpdateMessage(result.getErrorMessage());
                    result.setSuccess(false);
                }

                return result;
            } catch (SQLException e) {
                e.printStackTrace();
                result.setData(null);
                result.setErrorMessage(e.getMessage());
                result.setSuccess(false);
                return result;
            }
        }
        return result;
    }

    public void cleanEess() {
        try {
            Dao<Eess, Integer> asDao = getHelper().getEessDao();
            TableUtils.clearTable(asDao.getConnectionSource(), Eess.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
