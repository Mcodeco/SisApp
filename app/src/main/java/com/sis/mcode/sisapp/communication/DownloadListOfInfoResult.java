package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.Afiliado;

import java.util.ArrayList;

public class DownloadListOfInfoResult {

    private boolean success = false;
    private ArrayList<Afiliado> data;
    private String errorMessage;
    private String updateMessage;

    public DownloadListOfInfoResult() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Afiliado> getInfo() {
        return data;
    }

    public void setInfo(ArrayList<Afiliado> data) {
        this.data = data;
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

}