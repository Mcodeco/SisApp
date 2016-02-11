package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.TipAfi;

import java.util.ArrayList;

/**
 * Created by Consultor_ogti_27 on 20/01/2016.
 */
public class DownloadListOfTipAfiResult {

    private Boolean success = false;
    private ArrayList<TipAfi> data;
    private String errorMessage;
    private String updateMessage;

    public DownloadListOfTipAfiResult() {
        // TODO Auto-generated constructor stub
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<TipAfi> getData() {
        return data;
    }

    public void setData(ArrayList<TipAfi> data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
