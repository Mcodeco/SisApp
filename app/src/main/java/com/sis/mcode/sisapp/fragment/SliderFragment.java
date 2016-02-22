package com.sis.mcode.sisapp.fragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sis.mcode.sisapp.R;
import com.sis.mcode.sisapp.SliderActivity;

import java.io.File;

/**
 * Created by manuelmoyano on 2/21/16.
 */
public class SliderFragment extends Fragment {

    final static String LAYOUT_ID = "layoutid";

    public static SliderFragment newInstance(int layoutId) {

        SliderFragment pane = new SliderFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        int currentView = args.getInt("pos",0);

        View rootView = inflater.inflate(R.layout.fragment_slider, null);
        SliderActivity sliderActivity = (SliderActivity)getActivity();

        ImageView imageView = (ImageView) rootView.findViewById(R.id.image);

        ContextWrapper cw = sliderActivity.getContextWrapper();
        File pathImage = cw.getDir("sisapp", Context.MODE_PRIVATE);
        //String path = pathImage + "/" + MenuActivity.images.get(0)[1];
        //imageView.setImageURI(Uri.parse(path));

        Log.d("onCreateView", String.valueOf( currentView+ 1));

        ((TextView) rootView.findViewById(R.id.heading)).setText("Paso " + String.valueOf( currentView+ 1));
        //((TextView) rootView.findViewById(R.id.content)).setText(MenuActivity.images.get(currentView)[0]);

        return rootView;
    }

}
