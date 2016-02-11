package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfTipAfiResult;
import com.sis.mcode.sisapp.communication.DownloadListOfTipSegResult;
import com.sis.mcode.sisapp.entity.TipAfi;
import com.sis.mcode.sisapp.entity.TipSeg;
import com.sis.mcode.sisapp.service.impl.TipAfiServiceImpl;
import com.sis.mcode.sisapp.service.impl.TipSegServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

/**
 * Created by Consultor_ogti_27 on 20/01/2016.
 */

@ContextSingleton
public class TipAfiController {

    @Inject
    protected TipAfiServiceImpl _service;

    public List<TipAfi> getTipAfiList(String tipseg){
        return _service.getTipAfiList(tipseg);
    }

    public DownloadListOfTipAfiResult downloadTipAfi(){
        return _service.downloadTipAfi();
    }

    public void cleanTipAfi() {
        _service.cleanTipAfi();
    }
}
