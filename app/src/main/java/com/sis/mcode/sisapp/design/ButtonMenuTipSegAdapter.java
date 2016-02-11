package com.sis.mcode.sisapp.design;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.controller.TipSegController;
import com.sis.mcode.sisapp.entity.TipSeg;
import com.sis.mcode.sisapp.globals.Menu;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Consultor_ogti_27 on 07/01/2016.
 */
public class ButtonMenuTipSegAdapter extends BaseAdapter {

    private Context mContext;
    private MenuButton[] btns = new MenuButton[90];
    private int count;


    public ButtonMenuTipSegAdapter(Context c, List<TipSeg> lstTipSeg) throws IOException{
        mContext = c;
        this.count = lstTipSeg.size();
        for (int i=0;i<lstTipSeg.size();i++){
            btns[i]= new MenuButton(mContext, lstTipSeg.get(i).getNombre(), "20A8D3", 2);
            Menu.add(lstTipSeg.get(i).getNombre(), btns[i]);
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
