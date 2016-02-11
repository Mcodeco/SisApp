package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfReqAteResult;
import com.sis.mcode.sisapp.entity.ReqAte;
import com.sis.mcode.sisapp.service.impl.ReqAteServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class ReqAteController {

    @Inject
    protected ReqAteServiceImpl _service;

    public List<ReqAte> getReqAteList(){
        return _service.getReqAte();
    }

    public DownloadListOfReqAteResult downloadReqAte(){
        return _service.downloadReqAte();
    }

    public void cleanReqAfi() {
        _service.cleanReqAte();
    }

}