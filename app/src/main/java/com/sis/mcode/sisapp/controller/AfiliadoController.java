package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfInfoResult;
import com.sis.mcode.sisapp.entity.InfoDetail;
import com.sis.mcode.sisapp.service.impl.AfiliadoServiceImpl;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class AfiliadoController {

    @Inject
    protected AfiliadoServiceImpl _service;

    public DownloadListOfInfoResult getInfoByName(String firstLastname, String secondLastname, String firstName, String secondName) {
        return _service.getInfoByName(firstLastname, secondLastname, firstName, secondName);
    }

    public DownloadListOfInfoResult getInfoByDocNumber(int typeDocument, String documentNumber) {
        return _service.getInfoByDocNumber(typeDocument, documentNumber);
    }

    public InfoDetail getDetail(String table, int numReg, String numberDocument) {
        return _service.getDetail(table, numReg, numberDocument);
    }

}