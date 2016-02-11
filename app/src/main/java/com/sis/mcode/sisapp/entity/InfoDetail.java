package com.sis.mcode.sisapp.entity;

import com.google.gson.annotations.SerializedName;

public class InfoDetail {

    private boolean success = false;
    private String errorMessage;
    private String updateMessage;

    @SerializedName("ApPat")
    private String apellidoPaterno;

    @SerializedName("ApMat")
    private String apellidoMaterno;

    @SerializedName("Nombres")
    private String nombres;

    @SerializedName("FecNac")
    private String fechaNacimiento;

    @SerializedName("TipDoc")
    private String tipoDocumento;

    @SerializedName("NumDoc")
    private String documento;

    @SerializedName("Sexo")
    private String sexo;

    @SerializedName("Contrato")
    private String nroAfiliacion;

    @SerializedName("TipSeg")
    private String tipoSeguro;

    @SerializedName("Relacion")
    private String tipoAsegurado;

    @SerializedName("TipoFor")
    private String tipoFormato;

    @SerializedName("FecAfi")
    private String fechaAfiliacion;

    @SerializedName("Cobertura")
    private String planBeneficio;

    @SerializedName("Eess")
    private String establecimiento;

    @SerializedName("Estado")
    private String estado;

    @SerializedName("FecPag")
    private String hasta;

    @SerializedName("Tabla")
    private String tabla;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNroAfiliacion() {
        return nroAfiliacion;
    }

    public void setNroAfiliacion(String nroAfiliacion) {
        this.nroAfiliacion = nroAfiliacion;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getTipoAsegurado() {
        return tipoAsegurado;
    }

    public void setTipoAsegurado(String tipoAsegurado) {
        this.tipoAsegurado = tipoAsegurado;
    }

    public String getTipoFormato() {
        return tipoFormato;
    }

    public void setTipoFormato(String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public String getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(String fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public String getPlanBeneficio() {
        return planBeneficio;
    }

    public void setPlanBeneficio(String planBeneficio) {
        this.planBeneficio = planBeneficio;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }
}