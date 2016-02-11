package com.sis.mcode.sisapp.slidingmenu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.sis.mcode.sisapp.R;

/**
 * Created by Consultor_ogti_27 on 07/01/2016.
 */
public class MenuFragment extends Fragment {

    public MenuFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        GridView grdView = (GridView) rootView.findViewById(R.id.menuGridView);

        return rootView;
    }
}
