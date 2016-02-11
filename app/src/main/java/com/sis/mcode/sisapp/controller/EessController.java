package com.sis.mcode.sisapp.controller;

import android.util.Log;

//import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;
import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfEessResult;
import com.sis.mcode.sisapp.entity.Eess;
import com.sis.mcode.sisapp.service.impl.EessServiceImpl;

import java.util.List;

/**
 * Created by Consultor_ogti_27 on 28/01/2016.
 */
public class EessController {

    @Inject
    protected EessServiceImpl _service;


    public List<Eess> getEessList(){
        return _service.getEessList();
    }

    public DownloadListOfEessResult downloadEess(LatLng ll){
        return _service.downloadEess(ll);
    }

    public void cleanEess() {
        _service.cleanEess();
    }
}
