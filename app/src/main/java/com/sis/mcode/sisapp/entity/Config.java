package com.sis.mcode.sisapp.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Consultor_ogti_27 on 28/12/2015.
 */

@DatabaseTable(tableName="config")
public class Config {

    @DatabaseField(columnName="id", id=true)
    private Integer id;

    @DatabaseField(columnName="server")
    private String server;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServer() {
        // external
        return "200.60.55.89";
        //internal return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
