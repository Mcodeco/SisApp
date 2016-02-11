package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfReqAfiResult;
import com.sis.mcode.sisapp.entity.ReqAfi;
import com.sis.mcode.sisapp.service.impl.ReqAfiServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class ReqAfiController {

    @Inject
    protected ReqAfiServiceImpl _service;

    public List<ReqAfi> getReqAfiList(String tipAfi){
        return _service.getReqAfi(tipAfi);
    }

    public DownloadListOfReqAfiResult downloadReqAfi(){
        return _service.downloadReqAfi();
    }

    public void cleanReqAfi() {
        _service.cleanReqAfi();
    }

}