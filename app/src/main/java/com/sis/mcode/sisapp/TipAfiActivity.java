package com.sis.mcode.sisapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfTipAfiResult;
import com.sis.mcode.sisapp.communication.DownloadListOfTipSegResult;
import com.sis.mcode.sisapp.controller.TipAfiController;
import com.sis.mcode.sisapp.controller.TipSegController;
import com.sis.mcode.sisapp.design.ButtonMenuTipAfiAdapter;
import com.sis.mcode.sisapp.design.ButtonMenuTipSegAdapter;
import com.sis.mcode.sisapp.design.MenuButton;
import com.sis.mcode.sisapp.entity.TipAfi;
import com.sis.mcode.sisapp.entity.TipSeg;
import com.sis.mcode.sisapp.slidingmenu.fragment.MenuFragment;
import com.sis.mcode.sisapp.util.RoboActionBarActivity;

import java.io.File;
import java.util.List;

import roboguice.activity.RoboActivity;

public class TipAfiActivity extends RoboActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Inject
    protected TipAfiController tipAfiController;

    private SliderLayout mDemoSlider;

    List<TipAfi> lstTipAfi;

    TipSeg tipSeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_afi);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_back);
        myToolbar.setTitle(getIntent().getStringExtra("tipseg_name"));
        myToolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);

        tipSeg = new TipSeg();
        tipSeg.setId(getIntent().getStringExtra("tipseg_id"));
        tipSeg.setDescripcion(getIntent().getStringExtra("tipseg_desc"));
        tipSeg.setNombre(getIntent().getStringExtra("tipseg_name"));
        tipSeg.setImagen(getIntent().getStringExtra("tipseg_imagen"));

        if (savedInstanceState == null) {
            displayView();
        }

        showMenu();

    }

    private void showInfo(){
        Intent intent;
        intent = new Intent(this,DescActivity.class);
        intent.putExtra("tipseg_id", getIntent().getStringExtra("tipseg_id"));
        intent.putExtra("tipseg_name", getIntent().getStringExtra("tipseg_name"));
        intent.putExtra("tipseg_desc", getIntent().getStringExtra("tipseg_desc"));
        intent.putExtra("tipseg_imagen", getIntent().getStringExtra("tipseg_imagen"));
        this.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void showMenu(){
        new ShowMenuTask().execute();
    }

    public class ShowMenuTask extends AsyncTask<Void, Void, DownloadListOfTipAfiResult> {

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setTitle("Tipos de Afiliación");
            dialog.setMessage(getResources().getString(R.string.msg_please_wait));
            dialog.show();
        }

        @Override
        protected DownloadListOfTipAfiResult doInBackground(
                Void... params) {
            DownloadListOfTipAfiResult result = new DownloadListOfTipAfiResult();
            lstTipAfi = tipAfiController.getTipAfiList(tipSeg.getId());
            if (lstTipAfi.size()<=0){
                result = tipAfiController.downloadTipAfi();
            }else{
                result.setSuccess(true);
            }
            return result;
        }

        @Override
        protected void onPostExecute(DownloadListOfTipAfiResult result) {
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
            lstTipAfi = tipAfiController.getTipAfiList(tipSeg.getId());
            GridView grdView = (GridView) findViewById(R.id.menuGridView);

            Button btnTitle = (Button) findViewById(R.id.btnTitle);
            btnTitle.setText("INFORMACIÓN");
            btnTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TipAfiActivity.this, DescActivity.class);
                    intent.putExtra("name", tipSeg.getNombre());
                    intent.putExtra("description", tipSeg.getDescripcion());
                    intent.putExtra("image", tipSeg.getImagen());
                    startActivity(intent);
                }
            });

            ButtonMenuTipAfiAdapter buttonMenuTipAfiAdapter = new ButtonMenuTipAfiAdapter(this,lstTipAfi, tipSeg);
            grdView.setAdapter(buttonMenuTipAfiAdapter);

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

            mDemoSlider = (SliderLayout) findViewById(R.id.slider);

            List<TipAfi> tipAfiList = tipAfiController.getTipAfiList(tipSeg.getId());
            ContextWrapper cw = new ContextWrapper(TipAfiActivity.this);
            File images = cw.getDir("sisapp", Context.MODE_PRIVATE);

            for (TipAfi tipAfi : tipAfiList) {
                String name = images + "/" + tipAfi.getImagen();
                TextSliderView textSliderView = new TextSliderView(this);
                textSliderView
                        .description(tipAfi.getNombre())
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
        Integer opt = getIntent().getIntExtra("opt",0);
        Intent intent;

        switch (opt){
            case 0:
                intent = new Intent(this, DescActivity.class);
                intent.putExtra("name",lstTipAfi.get(i).getNombre());
                intent.putExtra("description", lstTipAfi.get(i).getDescripcion());
                intent.putExtra("image", lstTipAfi.get(i).getImagen());
                this.startActivity(intent);
                break;
            case 2:
                intent = new Intent(this,MenuActivity.class);
                intent.putExtra("tipafi_id",lstTipAfi.get(i).getId());
                intent.putExtra("tipafi_name", lstTipAfi.get(i).getNombre());
                this.startActivity(intent);
                break;
        }
    }
    private ProgressDialog dialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_question:
                showInfo();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayView() {

        Fragment fragment = null;
        fragment = new MenuFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        } else {
            Log.e("TipAfiActivity", "Error in creating fragment");
        }

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