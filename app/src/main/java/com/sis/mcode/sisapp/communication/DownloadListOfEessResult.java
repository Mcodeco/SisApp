package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.Eess;

import java.util.ArrayList;

public class DownloadListOfEessResult {

    private boolean success = false;
    private ArrayList<Eess> data;
    private String errorMessage;
    private String updateMessage;

    public DownloadListOfEessResult() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Eess> getData() {
        return data;
    }

    public void setData(ArrayList<Eess> data) {
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