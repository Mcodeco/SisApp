package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfSliderProcAteResult;
import com.sis.mcode.sisapp.entity.SliderProcAte;
import com.sis.mcode.sisapp.service.impl.ProcAteServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

/**
 * Created by manuelmoyano on 2/24/16.
 */
@ContextSingleton
public class ProcAteController {

    @Inject
    protected ProcAteServiceImpl _service;

    public List<SliderProcAte> getSliderProcAteList(){
        return _service.getSliderProcAteList();
    }

    public DownloadListOfSliderProcAteResult downloadSliderProcAte(){
        return _service.downloadSliderProcAte();
    }

    public void cleanSliderProcAte() {
        _service.cleanSliderProcAte();
    }
}
