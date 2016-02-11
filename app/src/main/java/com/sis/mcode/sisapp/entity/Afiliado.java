package com.sis.mcode.sisapp.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Afiliado {

    @SerializedName("ApPat")
    private String apPat;
    @SerializedName("ApMat")
    private String apMat;
    @SerializedName("Nombres")
    private String nombres;
    @SerializedName("Contrato")
    private String nroAfiliacion;
    @SerializedName("Dni")
    private String nroDocumento;
    @SerializedName("Relacion")
    private String tipoAsegurado;
    @SerializedName("Estado")
    private String estado;
    @SerializedName("Tabla")
    private String tabla;
    @SerializedName("NumReg")
    private String numReg;

    public String getApPat() {
        return apPat;
    }

    public void setApPat(String apPat) {
        this.apPat = apPat;
    }

    public String getApMat() {
        return apMat;
    }

    public void setApMat(String apMat) {
        this.apMat = apMat;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNroAfiliacion() {
        return nroAfiliacion;
    }

    public void setNroAfiliacion(String nroAfiliacion) {
        this.nroAfiliacion = nroAfiliacion;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getTipoAsegurado() {
        return tipoAsegurado;
    }

    public void setTipoAsegurado(String tipoAsegurado) {
        this.tipoAsegurado = tipoAsegurado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getNumReg() {
        return numReg;
    }

    public void setNumReg(String numReg) {
        this.numReg = numReg;
    }

    @Override
    public String toString() {
        return new String(new StringBuilder()
                .append("N° Afiliación: ")
                .append(this.nroAfiliacion)
                .append("\nN° Doc: ")
                .append(this.nroDocumento)
                .append("\nNombres y apellidos: ")
                .append(this.nombres).append(" ")
                .append(this.apPat).append(" ")
                .append(this.apMat)
                .append("\nTipo de asegurado: ")
                .append(this.tipoAsegurado)
                .append("\nEstado: ")
                .append(this.estado));
    }
}