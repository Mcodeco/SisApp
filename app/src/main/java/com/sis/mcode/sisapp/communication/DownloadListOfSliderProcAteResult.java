package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.SliderProcAte;

import java.util.ArrayList;

/**
 * Created by manuelmoyano on 2/24/16.
 */
public class DownloadListOfSliderProcAteResult {

    private Boolean success = false;
    private ArrayList<SliderProcAte> data;
    private String errorMessage;
    private String updateMessage;

    public DownloadListOfSliderProcAteResult() {
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

    public ArrayList<SliderProcAte> getData() {
        return data;
    }

    public void setData(ArrayList<SliderProcAte> data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
