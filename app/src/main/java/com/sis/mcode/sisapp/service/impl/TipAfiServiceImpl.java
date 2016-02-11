package com.sis.mcode.sisapp.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.communication.DownloadListOfTipAfiResult;
import com.sis.mcode.sisapp.communication.DownloadListOfTipSegResult;
import com.sis.mcode.sisapp.communication.ImageServices;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.entity.TipAfi;
import com.sis.mcode.sisapp.entity.TipSeg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Consultor_ogti_27 on 20/01/2016.
 */
public class TipAfiServiceImpl {

    private DBHelper dbhelper;

    @Inject
    private Context context;


    public List<TipAfi> getTipAfiList(String tipseg) {
        try {
            Dao<TipAfi, Integer> dao = getHelper().getTipAfiDao();
            QueryBuilder<TipAfi, Integer> builder = dao.queryBuilder();
            builder.setWhere(builder.where().eq("id_tipseg", tipseg));
            List<TipAfi> items = dao.query(builder.prepare());

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

    public DownloadListOfTipAfiResult downloadTipAfi(){

        ImageServices _service = new ImageServices();
        SoapServices service = new SoapServices(this.context);
        DownloadListOfTipAfiResult result = service.getTipAfi();

        if(result.isSuccess()){
            try {
                if(result.getData().size() > 0){
                    Dao<TipAfi, Integer> asDao = getHelper().getTipAfiDao();
                    TableUtils.clearTable(asDao.getConnectionSource(), TipAfi.class);

                    for(TipAfi as : result.getData()){
                        _service.saveImage(this.context, as.getImagen(), _service.downloadImage(as.getImagen()));
                        asDao.create(as);
                    }
                } else {
                    result.setErrorMessage("No hay tipos de afiliaciones registrados.");
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

    public void cleanTipAfi() {
        try {
            Dao<TipAfi, Integer> asDao = getHelper().getTipAfiDao();
            TableUtils.clearTable(asDao.getConnectionSource(), TipAfi.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
