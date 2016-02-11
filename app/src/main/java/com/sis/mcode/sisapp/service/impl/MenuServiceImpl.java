package com.sis.mcode.sisapp.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.sis.mcode.sisapp.communication.DownloadListOfEessResult;
import com.sis.mcode.sisapp.communication.DownloadListOfInfoResult;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.entity.Eess;
import com.sis.mcode.sisapp.entity.MenuItems;

import java.sql.SQLException;
import java.util.List;

public class MenuServiceImpl {

    private DBHelper dbhelper;

    @Inject
    private Context context;

    public List<MenuItems> getMenuList(Integer opt) {
        try {
            Dao<MenuItems, Integer> dao = getHelper().getMenuDao();
            QueryBuilder<MenuItems, Integer> builder = dao.queryBuilder();
            builder.setWhere(builder.where().eq("opt", opt));
            builder.orderBy("order",true);
            List<MenuItems> items = dao.query(builder.prepare());
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

    public void cleanMenu() {
        try {
            Dao<MenuItems, Integer> asDao = getHelper().getMenuDao();
            TableUtils.clearTable(asDao.getConnectionSource(), MenuItems.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}