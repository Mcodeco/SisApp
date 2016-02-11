package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfProcAteResult;
import com.sis.mcode.sisapp.entity.ProcAte;
import com.sis.mcode.sisapp.service.impl.ProcAteServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class ProcAteController {

    @Inject
    protected ProcAteServiceImpl _service;

    public List<ProcAte> getProcAteList(){
        return _service.getProcAte();
    }

    public DownloadListOfProcAteResult downloadProcAte(){
        return _service.downloadProcAte();
    }

    public void cleanTipAfi() {
        _service.cleanProcAte();
    }

}