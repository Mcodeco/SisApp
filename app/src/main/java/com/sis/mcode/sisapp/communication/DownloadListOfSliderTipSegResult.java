package com.sis.mcode.sisapp.communication;

import com.sis.mcode.sisapp.entity.SliderTipSeg;
import com.sis.mcode.sisapp.entity.TipSeg;

import java.util.ArrayList;

/**
 * Created by manuelmoyano on 2/23/16.
 */
public class DownloadListOfSliderTipSegResult {

    private Boolean success = false;
    private ArrayList<SliderTipSeg> data;
    private String errorMessage;
    private String updateMessage;

    public DownloadListOfSliderTipSegResult() {
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

    public ArrayList<SliderTipSeg> getData() {
        return data;
    }

    public void setData(ArrayList<SliderTipSeg> data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
