package com.sis.mcode.sisapp.service.impl;

import android.content.Context;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.application.SisApp;
import com.sis.mcode.sisapp.communication.DownloadListOfInfoResult;
import com.sis.mcode.sisapp.communication.DownloadListOfTipSegResult;
import com.sis.mcode.sisapp.communication.SoapServices;
import com.sis.mcode.sisapp.entity.InfoDetail;

public class AfiliadoServiceImpl {

    @Inject
    private Context context = SisApp.getAppContext();


    public DownloadListOfInfoResult getInfoByName(String firstLastname, String secondLastname, String firstName, String secondName) {

        SoapServices service = new SoapServices(this.context);
        DownloadListOfInfoResult result = service.getInfoByName(firstLastname, secondLastname, firstName, secondName);
        return result;

    }

    public DownloadListOfInfoResult getInfoByDocNumber(int typeDocument, String documentNumber) {

        SoapServices service = new SoapServices(this.context);
        DownloadListOfInfoResult result = service.getInfoByDocNumber(typeDocument, documentNumber);
        return result;

    }

    public InfoDetail getDetail(String table, int numReg, String numberDocument) {

        SoapServices service = new SoapServices(this.context);
        InfoDetail infoDetail = service.detail(table, numReg, numberDocument);
        return infoDetail;

    }

}