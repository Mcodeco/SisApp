package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfSliderProcAfiResult;
import com.sis.mcode.sisapp.entity.SliderProcAfi;
import com.sis.mcode.sisapp.service.impl.ProcAfiServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

/**
 * Created by manuelmoyano on 2/24/16.
 */
@ContextSingleton
public class ProcAfiController {

    @Inject
    protected ProcAfiServiceImpl _service;


    public List<SliderProcAfi> getSliderProcAfiList(){
        return _service.getSliderProcAfiList();
    }

    public List<SliderProcAfi> getSliderProcAfiById (int idTipSeg) {
        return _service.getSliderProcAfiById(idTipSeg);
    }

    public DownloadListOfSliderProcAfiResult downloadSliderProcAfi(){
        return _service.downloadSliderProcAfi();
    }

    public void cleanSliderProcAfi() {
        _service.cleanSliderProcAfi();
    }
}
