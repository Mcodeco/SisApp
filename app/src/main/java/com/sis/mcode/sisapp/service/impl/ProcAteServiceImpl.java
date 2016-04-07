package com.sis.mcode.sisapp.service.impl;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.application.SisApp;
import com.sis.mcode.sisapp.communication.DownloadListOfSliderProcAteResult;
import com.sis.mcode.sisapp.communication.ImageServices;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.entity.SliderProcAte;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by manuelmoyano on 2/24/16.
 */
public class ProcAteServiceImpl {
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

    public List<SliderProcAte> getSliderProcAteList() {
        try {
            Dao<SliderProcAte, Integer> dao = getHelper().getSliderProcAteDao();
            List<SliderProcAte> items = dao.queryForAll();
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public DownloadListOfSliderProcAteResult downloadSliderProcAte(){

        ImageServices _service = new ImageServices();
        SoapServices service = new SoapServices(this.context);
        DownloadListOfSliderProcAteResult result = service.getSliderProcAte();

        if(result.isSuccess()){
            try {
                if(result.getData().size() > 0){
                    Dao<SliderProcAte, Integer> asDao = getHelper().getSliderProcAteDao();
                    TableUtils.clearTable(asDao.getConnectionSource(), SliderProcAte.class);

                    for(SliderProcAte as : result.getData()) {
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

    public void cleanSliderProcAte() {
        try {
            Dao<SliderProcAte, Integer> asDao = getHelper().getSliderProcAteDao();
            TableUtils.clearTable(asDao.getConnectionSource(), SliderProcAte.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
