package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.TipSeg;

import java.util.ArrayList;

/**
 * Created by Consultor_ogti_27 on 28/12/2015.
 */
public class DownloadListOfTipSegResult {

    private Boolean success = false;
    private ArrayList<TipSeg> data;
    private String errorMessage;
    private String updateMessage;

    public DownloadListOfTipSegResult() {
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

    public ArrayList<TipSeg> getData() {
        return data;
    }

    public void setData(ArrayList<TipSeg> data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
