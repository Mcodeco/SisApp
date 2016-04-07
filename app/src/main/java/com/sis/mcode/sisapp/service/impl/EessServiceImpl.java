package com.sis.mcode.sisapp.service.impl;

import android.content.Context;
import android.util.Log;

//import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;
import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.application.SisApp;
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

    @Inject
    private Context context = SisApp.getAppContext();;

    public DownloadListOfEessResult downloadEess(LatLng ll){
        SoapServices service = new SoapServices(this.context);
        DownloadListOfEessResult result = service.getEess(ll);
        return result;
    }
}
