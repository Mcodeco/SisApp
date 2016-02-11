package com.sis.mcode.sisapp.design;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sis.mcode.sisapp.entity.MenuItems;
import com.sis.mcode.sisapp.globals.Menu;

import java.io.IOException;
import java.util.List;

/**
 * Created by Consultor_ogti_27 on 20/01/2016.
 */
public class ButtonMenuAdapter extends BaseAdapter{

    private Context mContext;
    private MenuButton[] btns = new MenuButton[90];
    private int count;


    public ButtonMenuAdapter(Context c, List<MenuItems> lstMenu) throws IOException {
        mContext = c;
        this.count = lstMenu.size();
        for (int i = 0; i < lstMenu.size(); i++) {
            if (lstMenu.size()%2 != 0) {
                if (i == lstMenu.size() - 1) {
                    btns[i] = new MenuButton(mContext, lstMenu.get(i).getItem(), "20A8D3", 1);
                } else {
                    btns[i] = new MenuButton(mContext, lstMenu.get(i).getItem(), "20A8D3", 2);
                }
            } else {
                btns[i] = new MenuButton(mContext, lstMenu.get(i).getItem(), "20A8D3", 2);
            }
            Menu.add(lstMenu.get(i).getItem(), btns[i]);
        }
    }

    public MenuButton[] get_menu_buttons()
    {
        return this.btns;
    }

    public int getCount() {
        return this.count;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        return btns[position];
    }
}