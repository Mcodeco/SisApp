package com.sis.mcode.sisapp.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.application.SisApp;
import com.sis.mcode.sisapp.communication.DownloadListOfSliderTipSegResult;
import com.sis.mcode.sisapp.communication.DownloadListOfTipSegResult;
import com.sis.mcode.sisapp.communication.ImageServices;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.entity.SliderTipSeg;
import com.sis.mcode.sisapp.entity.TipSeg;

import java.sql.SQLException;
import java.util.List;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TipSegServiceImpl {

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

    public List<TipSeg> getTipSegList() {
        try {
            Dao<TipSeg, Integer> dao = getHelper().getTipSegDao();
            List<TipSeg> items = dao.queryForAll();
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public DownloadListOfTipSegResult downloadTipSeg(){

        ImageServices _service = new ImageServices();
        SoapServices service = new SoapServices(this.context);
        DownloadListOfTipSegResult result = service.getTipSeg();

        if(result.isSuccess()){
            try {
                if(result.getData().size() > 0){
                    Dao<TipSeg, Integer> asDao = getHelper().getTipSegDao();
                    TableUtils.clearTable(asDao.getConnectionSource(), TipSeg.class);

                    for(TipSeg as : result.getData()) {
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

    public void cleanTipSeg() {
        try {
            Dao<TipSeg, Integer> asDao = getHelper().getTipSegDao();
            TableUtils.clearTable(asDao.getConnectionSource(), TipSeg.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SliderTipSeg> getSliderTipSegList() {
        try {
            Dao<SliderTipSeg, Integer> dao = getHelper().getSliderTipSegDao();
            List<SliderTipSeg> items = dao.queryForAll();
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SliderTipSeg> getSliderTipSegById(int idTipSeg) {
        try {
            Dao<SliderTipSeg, Integer> dao = getHelper().getSliderTipSegDao();
            QueryBuilder<SliderTipSeg, Integer> builder = dao.queryBuilder();
            builder.setWhere(builder.where().eq("id_tipseg", idTipSeg));
            builder.orderBy("order",true);
            List<SliderTipSeg> items = dao.query(builder.prepare());
            return items;
            } catch (SQLException e) {
            e.printStackTrace();
            }
        return null;
    }




    public DownloadListOfSliderTipSegResult downloadSliderTipSeg(){

        ImageServices _service = new ImageServices();
        SoapServices service = new SoapServices(this.context);
        DownloadListOfSliderTipSegResult result = service.getSliderTipSeg();

        if(result.isSuccess()){
            try {
                if(result.getData().size() > 0){
                    Dao<SliderTipSeg, Integer> asDao = getHelper().getSliderTipSegDao();
                    TableUtils.clearTable(asDao.getConnectionSource(), TipSeg.class);

                    for(SliderTipSeg as : result.getData()) {
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

    public void cleanSliderTipSeg() {
        try {
            Dao<SliderTipSeg, Integer> asDao = getHelper().getSliderTipSegDao();
            TableUtils.clearTable(asDao.getConnectionSource(), TipSeg.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
