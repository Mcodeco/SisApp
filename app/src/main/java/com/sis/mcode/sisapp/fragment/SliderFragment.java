package com.sis.mcode.sisapp.fragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sis.mcode.sisapp.MainActivity;
import com.sis.mcode.sisapp.R;
import com.sis.mcode.sisapp.SliderActivity;
import com.sis.mcode.sisapp.TipSegActivity;
import com.sis.mcode.sisapp.application.SisApp;
import com.sis.mcode.sisapp.communication.ImageServices;
import com.sis.mcode.sisapp.design.TouchImageView;

import java.io.File;
import java.io.InputStream;


/**
 * Created by manuelmoyano on 2/21/16.
 */
public class SliderFragment extends Fragment {

    final static String LAYOUT_ID = "layoutid";
    String img = "";
    ImageServices _service;
    SliderActivity sliderActivity;
    //ImageView imageView;
    TouchImageView imageView;

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
        int currentView = args.getInt("pos", 0);
        int currentActivity = args.getInt("opt", 0);

        View rootView = inflater.inflate(R.layout.fragment_slider, null);

        imageView = (TouchImageView) rootView.findViewById(R.id.image);

        _service = new ImageServices();
        sliderActivity = (SliderActivity)getActivity();

        ContextWrapper cw = sliderActivity.getContextWrapper();
        File pathImage = cw.getDir("sisapp", Context.MODE_PRIVATE);
        String path = "";
        img = "";
        if (currentActivity == 0 || currentActivity == 2){
            img = TipSegActivity.images.get(currentView)[0];
        } else if (currentActivity == 3) {
            img = MainActivity.images.get(currentView)[0];
        }
        path = pathImage + "/" + img;
        File file = new File(path);
        if(file.exists()){
            imageView.setImageURI(Uri.parse(path));
        } else {
            new DownloadImageTask(imageView).execute(_service.url + img);
        }

        return rootView;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            _service.saveImage(SisApp.getAppContext(), img, result);
        }
    }
}
