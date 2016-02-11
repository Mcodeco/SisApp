package com.sis.mcode.sisapp.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Consultor_ogti_27 on 28/12/2015.
 */
@DatabaseTable(tableName="app")
public class App {
    @DatabaseField(columnName="id", id=true)
    private Integer id;

    @DatabaseField(columnName="table_server")
    private String table_server;

    @DatabaseField(columnName="table_local")
    private String table_local;

    @DatabaseField(columnName="version")
    private int version;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTableServer() {
        return table_server;
    }

    public void setTableServer(String table_server) {
        this.table_server = table_server;
    }

    public String getTableLocal() {
        return table_local;
    }

    public void setTableLocal(String table_local) {
        this.table_local = table_local;
    }
}
