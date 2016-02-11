package com.sis.mcode.sisapp.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Consultor_ogti_27 on 20/01/2016.
 */
@DatabaseTable(tableName = "menu")
public class MenuItems {

    @DatabaseField(columnName="id", id=true)
    private Integer id;

    @DatabaseField(columnName="item")
    private String item;

    @DatabaseField(columnName="order")
    private Integer order;

    @DatabaseField(columnName = "opt")
    private Integer opt;

    public Integer getOpt() {
        return opt;
    }

    public void setOpt(Integer opt) {
        this.opt = opt;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
