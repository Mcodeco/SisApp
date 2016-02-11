package com.sis.mcode.sisapp.service.impl;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.communication.DownloadListOfProcAteResult;
import com.sis.mcode.sisapp.communication.ImageServices;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.entity.ProcAte;

import java.sql.SQLException;
import java.util.List;

public class ProcAteServiceImpl {

    private DBHelper dbhelper;

    @Inject
    private Context context;

    public DBHelper getHelper() {
        if (this.dbhelper == null) {
            this.dbhelper = OpenHelperManager.getHelper(this.context,
                    DBHelper.class);
        }
        return this.dbhelper;
    }

    public List<ProcAte> getProcAte() {
        try {
            Dao<ProcAte, Integer> dao = getHelper().getProcAteDao();
            QueryBuilder<ProcAte, Integer> builder = dao.queryBuilder();
            List<ProcAte> items = dao.query(builder.prepare());
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DownloadListOfProcAteResult downloadProcAte() {
        ImageServices _service = new ImageServices();
        SoapServices service = new SoapServices(this.context);
        DownloadListOfProcAteResult result = service.getProcAte();

        if(result.isSuccess()){
            try {
                if(result.getData().size() > 0){
                    Dao<ProcAte, Integer> asDao = getHelper().getProcAteDao();
                    TableUtils.clearTable(asDao.getConnectionSource(), ProcAte.class);

                    for(ProcAte as : result.getData()){
                        _service.saveImage(this.context, as.getImagen(), _service.downloadImage(as.getImagen()));
                        asDao.create(as);
                    }
                } else {
                    result.setErrorMessage("No hay procedimientos de atención registrados.");
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

    public void cleanProcAte() {
        try {
            Dao<ProcAte, Integer> asDao = getHelper().getProcAteDao();
            TableUtils.clearTable(asDao.getConnectionSource(), ProcAte.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}