package com.sis.mcode.sisapp.service.impl;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.application.SisApp;
import com.sis.mcode.sisapp.communication.DownloadListOfSliderProcAfiResult;
import com.sis.mcode.sisapp.communication.ImageServices;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.entity.SliderProcAfi;

import java.sql.SQLException;
import java.util.List;

import roboguice.inject.ContextSingleton;

/**
 * Created by manuelmoyano on 2/24/16.
 */
@ContextSingleton
public class ProcAfiServiceImpl {
    private DBHelper dbhelper;

    @Inject
    private Context context;

    public DBHelper getHelper() {
        if (this.dbhelper == null) {
            context = SisApp.getAppContext();
            this.dbhelper = OpenHelperManager.getHelper(this.context,
                    DBHelper.class);
        }
        return this.dbhelper;
    }

    public List<SliderProcAfi> getSliderProcAfiList() {
        try {
            Dao<SliderProcAfi, Integer> dao = getHelper().getSliderProcAfiDao();
            List<SliderProcAfi> items = dao.queryForAll();
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SliderProcAfi> getSliderProcAfiById(int idTipSeg) {
        try {
            Dao<SliderProcAfi, Integer> dao = getHelper().getSliderProcAfiDao();
            QueryBuilder<SliderProcAfi, Integer> builder = dao.queryBuilder();
            builder.setWhere(builder.where().eq("id_tipseg", idTipSeg));
            builder.orderBy("order",true);
            List<SliderProcAfi> items = dao.query(builder.prepare());
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    public DownloadListOfSliderProcAfiResult downloadSliderProcAfi(){

        ImageServices _service = new ImageServices();
        SoapServices service = new SoapServices(this.context);
        DownloadListOfSliderProcAfiResult result = service.getSliderProcAfi();

        if(result.isSuccess()){
            try {
                if(result.getData().size() > 0){
                    Dao<SliderProcAfi, Integer> asDao = getHelper().getSliderProcAfiDao();
                    TableUtils.clearTable(asDao.getConnectionSource(), SliderProcAfi.class);

                    for(SliderProcAfi as : result.getData()) {
                        //_service.saveImage(this.context, as.getImagen(), _service.downloadImage(as.getImagen()));
                        asDao.create(as);
                    }
                } else {
                    result.setErrorMessage("No hay tipos de seguros registrados.");
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

    public void cleanSliderProcAfi() {
        try {
            Dao<SliderProcAfi, Integer> asDao = getHelper().getSliderProcAfiDao();
            TableUtils.clearTable(asDao.getConnectionSource(), SliderProcAfi.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
