package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.ReqAfi;

import java.util.ArrayList;

public class DownloadListOfReqAfiResult {

    private Boolean success = false;
    private ArrayList<ReqAfi> data;
    private String errorMessage;
    private String updateMessage;

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<ReqAfi> getData() {
        return data;
    }

    public void setData(ArrayList<ReqAfi> data) {
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