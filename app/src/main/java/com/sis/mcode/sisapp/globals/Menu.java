package com.sis.mcode.sisapp.globals;

import com.sis.mcode.sisapp.design.MenuButton;

import java.util.HashMap;

/**
 * Created by Consultor_ogti_27 on 07/01/2016.
 */
public class Menu {
    public static final HashMap<String, MenuButton> menus = new HashMap<String, MenuButton>();

    private Menu() {}

    public static void add(String name, MenuButton obj)
    {
        menus.put(name, obj);
    }

    public static MenuButton get(String name)
    {
        return menus.get(name);
    }
}
