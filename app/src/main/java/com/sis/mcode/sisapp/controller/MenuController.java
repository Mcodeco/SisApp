package com.sis.mcode.sisapp.controller;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.entity.InfoDetail;
import com.sis.mcode.sisapp.entity.MenuItems;
import com.sis.mcode.sisapp.service.impl.MenuServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class MenuController {

    @Inject
    protected MenuServiceImpl _service;

    public List<MenuItems> getMenuList(int opt){
        return _service.getMenuList(opt);
    }

    public void cleanMenu() {
        _service.cleanMenu();
    }

}