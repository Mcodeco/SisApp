package com.sis.mcode.sisapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfTipSegResult;
import com.sis.mcode.sisapp.controller.TipSegController;
import com.sis.mcode.sisapp.design.ButtonMenuTipSegAdapter;
import com.sis.mcode.sisapp.entity.TipSeg;
import com.sis.mcode.sisapp.slidingmenu.fragment.MenuFragment;
import com.sis.mcode.sisapp.util.RoboActionBarActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class TipSegActivity extends RoboActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Inject
    protected TipSegController tipSegController;

    private SliderLayout mDemoSlider;

    List<TipSeg> lstTipSeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_seg);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_back);
        myToolbar.setTitle(getIntent().getStringExtra("title"));
        myToolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);

        if (savedInstanceState == null) {
            displayView();
        }

        showMenu();

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
        new ShowMenuTask().execute();
    }

    public class ShowMenuTask extends AsyncTask<Void, Void, DownloadListOfTipSegResult> {

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setTitle("Nuestros Seguros");
            dialog.setMessage(getResources().getString(R.string.msg_please_wait));
            dialog.show();
        }

        @Override
        protected DownloadListOfTipSegResult doInBackground(
                Void... params) {
            DownloadListOfTipSegResult result = new DownloadListOfTipSegResult();
            lstTipSeg = tipSegController.getTipSegList();
            if (lstTipSeg.size()<=0){
                result = tipSegController.downloadTipSeg();
            }else{
                result.setSuccess(true);
            }
            return result;
        }
        @Override
        protected void onPostExecute(DownloadListOfTipSegResult result) {
            dialog.dismiss();
            if (result.isSuccess()){
                createMenu();
            }else {
                if(result.getErrorMessage() != null){
                    showMessage(result.getErrorMessage(),Toast.LENGTH_LONG);
                }
                finish();
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
                textSliderView
                        .description(tipSeg.getNombre())
                        .image(new File(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

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

    private void showMessage(String text, int length){
        Toast.makeText(this, text, length).show();
    }

    private void openScreen(int i){
        Intent intent = new Intent(this,TipAfiActivity.class);
        intent.putExtra("tipseg_id", lstTipSeg.get(i).getId());
        intent.putExtra("tipseg_name", lstTipSeg.get(i).getNombre());
        intent.putExtra("tipseg_desc", lstTipSeg.get(i).getDescripcion());
        intent.putExtra("tipseg_imagen", lstTipSeg.get(i).getImagen());
        intent.putExtra("opt",getIntent().getIntExtra("opt",0));
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