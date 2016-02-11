package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.ProcAfi;

import java.util.ArrayList;

public class DownloadListOfProcAfiResult {

    private Boolean success = false;
    private ArrayList<ProcAfi> data;
    private String errorMessage;
    private String updateMessage;

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<ProcAfi> getData() {
        return data;
    }

    public void setData(ArrayList<ProcAfi> data) {
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