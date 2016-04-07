package com.sis.mcode.sisapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfSliderProcAfiResult;
import com.sis.mcode.sisapp.communication.DownloadListOfSliderTipSegResult;
import com.sis.mcode.sisapp.communication.DownloadListOfTipSegResult;
import com.sis.mcode.sisapp.communication.ImageServices;
import com.sis.mcode.sisapp.controller.ProcAfiController;
import com.sis.mcode.sisapp.controller.TipSegController;
import com.sis.mcode.sisapp.design.ButtonMenuTipSegAdapter;
import com.sis.mcode.sisapp.entity.SliderProcAfi;
import com.sis.mcode.sisapp.entity.SliderTipSeg;
import com.sis.mcode.sisapp.entity.TipSeg;
import com.sis.mcode.sisapp.slidingmenu.fragment.MenuFragment;
import com.sis.mcode.sisapp.util.RoboActionBarActivity;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TipSegActivity extends RoboActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Inject
    protected TipSegController tipSegController;

    @Inject
    protected ProcAfiController procAfiController;

    private SliderLayout mDemoSlider;

    List<TipSeg> lstTipSeg;
    List<SliderTipSeg> lstSliderTipSeg;
    List<SliderProcAfi> lstSliderProcAfi;

    public static List<String[]> images;
    ImageServices _service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_seg);

        images = new ArrayList<String[]>();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher);
        myToolbar.setNavigationIcon(R.mipmap.ic_back);
        myToolbar.setTitle(" | " + getIntent().getStringExtra("title"));
        myToolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);
        _service = new ImageServices();

        if (savedInstanceState == null) {
            displayView();
        }
        showMenu();
    }

    private void initImages(int idTipSeg) {
        int opt = getIntent().getIntExtra("opt", 0);
        images.clear();

        if (opt == 0) {
            lstSliderTipSeg = tipSegController.getSliderTipSegById(idTipSeg);
            if (lstSliderTipSeg.size() > 0){
                for (SliderTipSeg sliderTipSeg: lstSliderTipSeg) {
                    String[] image = new String[1];
                    image[0] = sliderTipSeg.getImagen();
                    images.add(image);
                }
            }
        } else if (opt == 2) {
            lstSliderProcAfi = procAfiController.getSliderProcAfiById(idTipSeg);
            if (lstSliderProcAfi.size() > 0){
                for (SliderProcAfi sliderProcAfi: lstSliderProcAfi) {
                    String[] image = new String[1];
                    image[0] = sliderProcAfi.getImagen();
                    images.add(image);
                }
            }
        }
    }


    private void displayView() {

        Fragment fragment = null;
        fragment = new MenuFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        } else {
            Log.e("TipSegActivity", "Error in creating fragment");
        }

    }

    public void showMenu(){
        new ShowMenuTask().execute(0); // Tipos de Seguro
        new ShowMenuTask().execute(1); // Descripción de Tipos de Seguro
        new ShowMenuTask().execute(2); // Procedimientos de Afiliación
    }

    public class ShowMenuTask extends AsyncTask<Integer, Void, Object> {

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setTitle("Nuestros Seguros");
            dialog.setMessage(getResources().getString(R.string.msg_please_wait));
            dialog.show();
        }

        @Override
        protected Object doInBackground(
                Integer... params) {

            switch (params[0]){
                case 0:
                    DownloadListOfTipSegResult result = new DownloadListOfTipSegResult();
                    lstTipSeg = tipSegController.getTipSegList();
                    if (lstTipSeg.size()<=0){
                        result = tipSegController.downloadTipSeg();
                    }else{
                        result.setSuccess(true);
                    }
                    return result;

                case 1:
                    DownloadListOfSliderTipSegResult rslt = new DownloadListOfSliderTipSegResult();
                    lstSliderTipSeg = tipSegController.getSliderTipSegList();
                    if (lstSliderTipSeg.size()<=0){
                        rslt = tipSegController.downloadSliderTipSeg();
                    }else{
                        rslt.setSuccess(true);
                    }
                    return rslt;

                case 2:
                    DownloadListOfSliderProcAfiResult rlt = new DownloadListOfSliderProcAfiResult();
                    lstSliderProcAfi = procAfiController.getSliderProcAfiList();
                    if (lstSliderProcAfi.size()<=0){
                        rlt = procAfiController.downloadSliderProcAfi();
                    }else{
                        rlt.setSuccess(true);
                    }
                    return rlt;
            }
            return null;


        }
        @Override
        protected void onPostExecute(Object result) {
            if (result instanceof DownloadListOfTipSegResult) {
                DownloadListOfTipSegResult rslt = (DownloadListOfTipSegResult)result;
                if (rslt.isSuccess()) {
                    createMenu();
                } else {
                    if (rslt.getErrorMessage() != null) {
                        showMessage(((DownloadListOfTipSegResult) result).getErrorMessage(),Toast.LENGTH_LONG);
                    }
                    finish();
                }
            } else if (result instanceof DownloadListOfSliderProcAfiResult){
                DownloadListOfSliderProcAfiResult rslt = (DownloadListOfSliderProcAfiResult)result;
                if(rslt.isSuccess()){
                    dialog.dismiss();
                }
            }
        }
    }

    public void createMenu() {
        try {
            lstTipSeg = tipSegController.getTipSegList();
            GridView grdView = (GridView) findViewById(R.id.menuGridView);

            ButtonMenuTipSegAdapter buttonMenuTipSegAdapter = new ButtonMenuTipSegAdapter(this,lstTipSeg);
            grdView.setAdapter(buttonMenuTipSegAdapter);

            grdView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    try {
                        openScreen(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            mDemoSlider = (SliderLayout)findViewById(R.id.slider);

            List<TipSeg> tipSegList = tipSegController.getTipSegList();
            ContextWrapper cw = new ContextWrapper(TipSegActivity.this);
            File images = cw.getDir("sisapp", Context.MODE_PRIVATE);

            for (TipSeg tipSeg : tipSegList) {
                String name = images + "/" + tipSeg.getImagen();
                TextSliderView textSliderView = new TextSliderView(this);

                File file = new File(name);

                if(!file.exists()){
                    textSliderView.image(_service.url + tipSeg.getImagen());
                    new DownloadImageTask().execute(_service.url + tipSeg.getImagen()+"@"+tipSeg.getImagen());
                }else {
                    textSliderView.image(file);
                }

                textSliderView
                        .description(tipSeg.getNombre())
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .bundle(new Bundle())
                        .getBundle().putString("extra", name);

                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);

        } catch (Exception e) {
            showMessage(e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, ResponseImage> {

        protected ResponseImage doInBackground(String... urls) {
            String urldisplay = urls[0].split("@")[0];
            String img = urls[0].split("@")[1];
            ResponseImage responseImage = new ResponseImage();
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                responseImage.setResult(BitmapFactory.decodeStream(in));
                responseImage.setImg(img);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return responseImage;
        }

        protected void onPostExecute(ResponseImage result) {
            _service.saveImage(getApplicationContext(), result.getImg(), result.getResult());
        }
    }

    private class ResponseImage {
        private Bitmap result;
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Bitmap getResult() {
            return result;
        }

        public void setResult(Bitmap result) {
            this.result = result;
        }
    }

    private void showMessage(String text, int length){
        Toast.makeText(this, text, length).show();
    }

    private void openScreen(int i){
        int idTipSeg = Integer.parseInt(lstTipSeg.get(i).getId());
        initImages(idTipSeg);

        Intent intent = new Intent(this,SliderActivity.class);
        intent.putExtra("opt", getIntent().getIntExtra("opt", 0));
        intent.putExtra("tipseg_id",lstTipSeg.get(i).getId());
        intent.putExtra("pages", images.size());

        this.startActivity(intent);

    }

    private ProgressDialog dialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}