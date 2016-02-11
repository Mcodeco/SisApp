package com.sis.mcode.sisapp.service.impl;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.communication.DownloadListOfReqAteResult;
import com.sis.mcode.sisapp.communication.ImageServices;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.entity.ReqAte;

import java.sql.SQLException;
import java.util.List;

public class ReqAteServiceImpl {

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

    public List<ReqAte> getReqAte() {
        try {
            Dao<ReqAte, Integer> dao = getHelper().getReqAteDao();
            QueryBuilder<ReqAte, Integer> builder = dao.queryBuilder();
            List<ReqAte> items = dao.query(builder.prepare());
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DownloadListOfReqAteResult downloadReqAte() {
        ImageServices _service = new ImageServices();
        SoapServices service = new SoapServices(this.context);
        DownloadListOfReqAteResult result = service.getReqAte();

        if(result.isSuccess()){
            try {
                if(result.getData().size() > 0){
                    Dao<ReqAte, Integer> asDao = getHelper().getReqAteDao();
                    TableUtils.clearTable(asDao.getConnectionSource(), ReqAte.class);

                    for(ReqAte as : result.getData()){
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

    public void cleanReqAte() {
        try {
            Dao<ReqAte, Integer> asDao = getHelper().getReqAteDao();
            TableUtils.clearTable(asDao.getConnectionSource(), ReqAte.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}