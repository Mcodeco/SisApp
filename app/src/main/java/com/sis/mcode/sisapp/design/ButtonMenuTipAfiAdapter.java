package com.sis.mcode.sisapp.design;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sis.mcode.sisapp.entity.TipAfi;
import com.sis.mcode.sisapp.entity.TipSeg;
import com.sis.mcode.sisapp.globals.Menu;

import java.io.IOException;
import java.util.List;

public class ButtonMenuTipAfiAdapter extends BaseAdapter{

    private Context mContext;
    private MenuButton[] btns = new MenuButton[90];
    private int count;


    public ButtonMenuTipAfiAdapter(Context c, List<TipAfi> lstTipAfi, TipSeg tipSeg) throws IOException {
        mContext = c;
        this.count = lstTipAfi.size();

        for (int i = 0; i < lstTipAfi.size(); i++) {
            btns[i] = new MenuButton(mContext, lstTipAfi.get(i).getNombre(), "20A8D3", 2);
            Menu.add(lstTipAfi.get(i).getNombre(), btns[i]);
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