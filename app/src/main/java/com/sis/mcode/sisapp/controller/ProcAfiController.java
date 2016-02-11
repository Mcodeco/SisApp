package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfProcAfiResult;
import com.sis.mcode.sisapp.entity.ProcAfi;
import com.sis.mcode.sisapp.service.impl.ProcAfiServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class ProcAfiController {

    @Inject
    protected ProcAfiServiceImpl _service;

    public List<ProcAfi> getProcAfiList(String tipAfi){
        return _service.getProcAfi(tipAfi);
    }

    public DownloadListOfProcAfiResult downloadProcAfi(){
        return _service.downloadProcAfi();
    }

    public void cleanTipAfi() {
        _service.cleanProcAfi();
    }

}