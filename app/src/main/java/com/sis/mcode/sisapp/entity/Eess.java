package com.sis.mcode.sisapp.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class Eess {

    public static final String ID_FIELD_NAME = "id";
    public static final String SISCODE_FIELD_NAME = "siscode";
    public static final String DESCRIPCION_FIELD_NAME = "description";
    public static final String DIRECCION_FIELD_NAME = "address";
    public static final String LATITUDE_FIELD_NAME = "x";
    public static final String LONGITUDE_FIELD_NAME = "y";

    @SerializedName("Id")
    private String id;

    @SerializedName("IdSis")
    private String idSis;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("Direccion")
    private String direccion;


    @SerializedName("X")
    private double latitude;

    @SerializedName("Y")
    private double longitud;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSis() {
        return idSis;
    }

    public void setIdSis(String idSis) {
        this.idSis = idSis;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}