package com.sis.mcode.sisapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
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
import com.sis.mcode.sisapp.controller.MenuController;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.design.ButtonMenuAdapter;
import com.sis.mcode.sisapp.entity.MenuItems;
import com.sis.mcode.sisapp.slidingmenu.fragment.MenuFragment;
import com.sis.mcode.sisapp.util.PrefConstants;
import com.sis.mcode.sisapp.util.RoboActionBarActivity;
import com.sis.mcode.sisapp.util.SAppUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;

import roboguice.inject.ContentView;

public class MainActivity extends RoboActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private SliderLayout mDemoSlider;

    @Inject
    protected MenuController menuController;

    List<MenuItems> lstMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkShowTutorial();
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher);
        myToolbar.setTitle(" | MENU PRINCIPAL");
        myToolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("SIS Subsidiado",R.drawable.sis_subsidiado);
        file_maps.put("SIS Independiente",R.drawable.sis_ind);
        file_maps.put("SIS Emprendedor",R.drawable.sis_emp);
        file_maps.put("SIS Microempresas", R.drawable.sis_mypes);
        file_maps.put("Plan de Salud Escolar", R.drawable.sis_planesc);


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        try {
            if(!DBHelper.checkDataBase(this)){
                DBHelper.copyDataBase(this);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error copiando la base de datos.", Toast.LENGTH_SHORT).show();
        }

        dialog = new ProgressDialog(this);

        if (savedInstanceState == null) {
            displayView();
        }

        downloadInformation();

    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
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

    private void displayView() {

        Fragment fragment = null;
        fragment = new MenuFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

    }

    public void createMenu() {
        try {
            lstMenu = menuController.getMenuList(1);
            GridView grdView = (GridView) findViewById(R.id.menuGridView);

            ButtonMenuAdapter buttonMenuAdapter = new ButtonMenuAdapter(this,lstMenu);
            grdView.setAdapter(buttonMenuAdapter);

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
        } catch (Exception e) {
            showMessage(e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void openScreen(int i){
        Intent intent;
        switch (i){
            case 0:
                intent= new Intent(this,TipSegActivity.class);
                intent.putExtra("opt",i);
                intent.putExtra("title",lstMenu.get(i).getItem());
                this.startActivity(intent);
                break;
            case 1:
                intent = new Intent(this,MapsActivity.class);
                intent.putExtra("title", lstMenu.get(i).getItem());
                this.startActivity(intent);
                break;
            case 2:
                intent = new Intent(this,TipSegActivity.class);
                intent.putExtra("opt",i);
                intent.putExtra("title",lstMenu.get(i).getItem());
                this.startActivity(intent);
                break;
            case 3:
                intent = new Intent(this,MenuActivity.class);
                intent.putExtra("opt",i);
                intent.putExtra("title",lstMenu.get(i).getItem());
                this.startActivity(intent);
                break;
            case 4:
                intent = new Intent(this,ConsultaActivity.class);
                intent.putExtra("title",lstMenu.get(i).getItem());
                this.startActivity(intent);
                break;
        }
    }

    public void downloadInformation(){
        new DownloadInformationTask().execute();
    }

    private void checkShowTutorial(){
        int oldVersionCode = PrefConstants.getAppPrefInt(this, "version_code");
        int currentVersionCode = SAppUtil.getAppVersionCode(this);

        if(currentVersionCode>oldVersionCode){
            startActivity(new Intent(MainActivity.this,ProductTourActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            PrefConstants.putAppPrefInt(this, "version_code", currentVersionCode);
        }
    }
    private void showTutorial(){
        startActivity(new Intent(MainActivity.this, ProductTourActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private ProgressDialog dialog;

    public class DownloadInformationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setTitle("Bienvenido");
            dialog.setMessage(getResources().getString(R.string.msg_please_wait));
            dialog.show();
        }

        @Override
        protected String doInBackground(
                Void... params) {
                return String.valueOf("true@noerrors");
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            String[] rslt = result.split("@");
            if(rslt[0].equals("true")) {
                createMenu();
            } else {
                if(rslt[1] != null){
                    showMessage(rslt[1],Toast.LENGTH_LONG);
                }
                finish();
            }

        }
    }

    private void showMessage(String text, int length){
        Toast.makeText(this, text, length).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //menu.getItem(0).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_question:
                showTutorial();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}