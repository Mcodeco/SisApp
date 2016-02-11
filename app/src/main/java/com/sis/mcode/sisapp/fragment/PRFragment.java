package com.sis.mcode.sisapp.fragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sis.mcode.sisapp.MenuActivity;
import com.sis.mcode.sisapp.R;
import com.sis.mcode.sisapp.WizardPRActivity;


import org.anddev.andengine.util.constants.Constants;

import java.io.File;

public class PRFragment extends Fragment {

    final static String LAYOUT_ID = "layoutid";

    public static PRFragment newInstance(int layoutId) {
        Log.d("newInstance", String.valueOf(layoutId));
        PRFragment pane = new PRFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);

        Bundle args = getArguments();
        int currentView = args.getInt("pos",0);

        View rootView = inflater.inflate(R.layout.fragment_pr, null);
        WizardPRActivity wizardPRActivity = (WizardPRActivity)getActivity();

        ImageView imageView = (ImageView) rootView.findViewById(R.id.image);

        ContextWrapper cw = wizardPRActivity.getContextWrapper();
        File pathImage = cw.getDir("sisapp", Context.MODE_PRIVATE);
        String path = pathImage + "/" + MenuActivity.images.get(0)[1];
        imageView.setImageURI(Uri.parse(path));

        Log.d("onCreateView", String.valueOf( currentView+ 1));

        ((TextView) rootView.findViewById(R.id.heading)).setText("Paso " + String.valueOf( currentView+ 1));
        ((TextView) rootView.findViewById(R.id.content)).setText(MenuActivity.images.get(currentView)[0]);

        return rootView;
    }

}