package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.SliderProcAfi;

import java.util.ArrayList;

/**
 * Created by manuelmoyano on 2/24/16.
 */
public class DownloadListOfSliderProcAfiResult {
    private Boolean success = false;
    private ArrayList<SliderProcAfi> data;
    private String errorMessage;
    private String updateMessage;

    public DownloadListOfSliderProcAfiResult() {
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

    public ArrayList<SliderProcAfi> getData() {
        return data;
    }

    public void setData(ArrayList<SliderProcAfi> data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
