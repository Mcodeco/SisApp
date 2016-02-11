package com.sis.mcode.sisapp.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tipafi")
public class TipAfi {

    public static final String ID_FIELD_NAME = "id";
    public static final String ID_TIPSEG_FIELD_NAME = "id_tipseg";
    public static final String NAME_FIELD_NAME = "name";
    public static final String DESCRIPTION_FIELD_NAME = "description";
    public static final String ORDER_FIELD_NAME = "order";
    public static final String IMAGE_FIELD_NAME = "image";


    @DatabaseField(id=true,columnName = ID_FIELD_NAME, unique = true)
    @SerializedName("Id")
    private String id;

    @SerializedName("IdTipoSeguro")
    @DatabaseField(columnName=ID_TIPSEG_FIELD_NAME)
    private String id_tipseg;

    @SerializedName("Nombre")
    @DatabaseField(columnName = NAME_FIELD_NAME)
    private String nombre;

    @SerializedName("Descripcion")
    @DatabaseField(columnName=DESCRIPTION_FIELD_NAME)
    private String descripcion;

    @SerializedName("Orden")
    @DatabaseField(columnName=ORDER_FIELD_NAME)
    private String orden;

    @SerializedName("Imagen")
    @DatabaseField(columnName=IMAGE_FIELD_NAME)
    private String imagen;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTipSeg() {
        return id_tipseg;
    }

    public void setIdTipSeg(String id_tipseg) {
        this.id_tipseg = id_tipseg;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}