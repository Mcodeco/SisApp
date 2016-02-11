package com.sis.mcode.sisapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.sis.mcode.sisapp.communication.DownloadListOfProcAfiResult;
import com.sis.mcode.sisapp.communication.DownloadListOfProcAteResult;
import com.sis.mcode.sisapp.communication.DownloadListOfReqAfiResult;
import com.sis.mcode.sisapp.communication.DownloadListOfReqAteResult;
import com.sis.mcode.sisapp.controller.MenuController;
import com.sis.mcode.sisapp.controller.ProcAfiController;
import com.sis.mcode.sisapp.controller.ProcAteController;
import com.sis.mcode.sisapp.controller.ReqAfiController;
import com.sis.mcode.sisapp.controller.ReqAteController;
import com.sis.mcode.sisapp.db.DBHelper;
import com.sis.mcode.sisapp.design.ButtonMenuAdapter;
import com.sis.mcode.sisapp.entity.MenuItems;
import com.sis.mcode.sisapp.entity.ProcAfi;
import com.sis.mcode.sisapp.entity.ProcAte;
import com.sis.mcode.sisapp.entity.ReqAfi;
import com.sis.mcode.sisapp.entity.ReqAte;
import com.sis.mcode.sisapp.slidingmenu.fragment.MenuFragment;
import com.sis.mcode.sisapp.util.RoboActionBarActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends RoboActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Inject
    protected MenuController menuController;
    @Inject
    protected ProcAfiController procAfiController;
    @Inject
    protected ReqAfiController reqAfiController;
    @Inject
    protected ProcAteController procAteController;
    @Inject
    protected ReqAteController reqAteController;

    private ProgressDialog dialog;
    private SliderLayout mDemoSlider;

    List<ProcAfi> procAfiList;
    List<ReqAfi> reqAfiList;
    List<ProcAte> procAteList;
    List<ReqAte> reqAteList;
    List<MenuItems> lstMenu;

    String title;
    String from;

    Button button;

    public static List<String[]> images = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dialog = new ProgressDialog(this);

        button = (Button)findViewById(R.id.btnSection);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_back);
        title = getIntent().getStringExtra("tipafi_name");
        if (title == null) {
            title = "¿CÓMO ME ATIENDO?";
            from = "atencion";
            procAteList = procAteController.getProcAteList();
            reqAteList = reqAteController.getReqAteList();
            button.setText("¿CÓMO ATENDERSE?");
            downloadInformationAtencion();
        } else {
            from = "afiliacion";
            procAfiList = procAfiController.getProcAfiList(getIntent().getStringExtra("tipafi_id"));
            reqAfiList = reqAfiController.getReqAfiList(getIntent().getStringExtra("tipafi_id"));
            button.setText("¿CÓMO AFILIARSE?");
            downloadInformationAfiliacion();
        }
        myToolbar.setTitle(title);

        initIMages();

        myToolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            if(!DBHelper.checkDataBase(this)) {
                DBHelper.copyDataBase(this);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error copiando la base de datos.", Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState == null) {
            displayView();
        }

    }

    private void initIMages() {

        if (procAfiList != null) {
            for (ProcAfi procAfi : procAfiList) {
                String[] image = new String[2];
                image[0] = procAfi.getDescripcion();
                image[1] = procAfi.getImagen();
                images.add(image);
            }
        } else if (reqAfiList != null) {
            for (ReqAfi reqAfi : reqAfiList) {
                String[] image = new String[2];
                image[0] = reqAfi.getDescripcion();
                image[1] = reqAfi.getImagen();
                images.add(image);
            }
        } else if (procAteList != null) {
            for (ProcAte procAte : procAteList) {
                String[] image = new String[2];
                image[0] = procAte.getDescripcion();
                image[1] = procAte.getImagen();
                images.add(image);
            }
        } else if (reqAteList != null) {
            for (ReqAte reqAte : reqAteList) {
                String[] image = new String[2];
                image[0] = reqAte.getDescripcion();
                image[1] = reqAte.getImagen();
                images.add(image);
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
            Log.e("MenuActivity", "Error in creating fragment");
        }
    }

    public void downloadInformationAfiliacion() {
        new DownloadInformationTask().execute(0);
        new DownloadInformationTask().execute(1);
    }

    public void downloadInformationAtencion() {
        new DownloadInformationTask().execute(2);
        new DownloadInformationTask().execute(3);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    public class DownloadInformationTask extends AsyncTask<Integer, Void, Object> {

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setTitle("Bienvenido");
            dialog.setMessage(getResources().getString(R.string.msg_please_wait));
            dialog.show();
        }

        @Override
        protected Object doInBackground(Integer... params) {
            switch (params[0]) {
                case 0: {
                    DownloadListOfProcAfiResult result = new DownloadListOfProcAfiResult();
                    if (procAfiList.size() != 0) {
                        result.setSuccess(true);
                    } else {
                        result = procAfiController.downloadProcAfi();
                    }
                    return result;
                }
                case 1: {
                    DownloadListOfReqAfiResult result = new DownloadListOfReqAfiResult();
                    if (reqAfiList.size() != 0) {
                        result.setSuccess(true);
                    } else {
                        result = reqAfiController.downloadReqAfi();
                    }
                    return result;
                }
                case 2: {
                    DownloadListOfProcAteResult result = new DownloadListOfProcAteResult();
                    if (procAteList.size() != 0) {
                        result.setSuccess(true);
                    } else {
                        result = procAteController.downloadProcAte();
                    }
                    return result;
                }
                case 3: {
                    DownloadListOfReqAteResult result = new DownloadListOfReqAteResult();
                    if (reqAteList.size() != 0) {
                        result.setSuccess(true);
                    } else {
                        result = reqAteController.downloadReqAte();
                    }
                    return result;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            dialog.dismiss();

            mDemoSlider = (SliderLayout) findViewById(R.id.slider);

            ContextWrapper cw = new ContextWrapper(MenuActivity.this);
            File images = cw.getDir("sisapp", Context.MODE_PRIVATE);

            if (result instanceof DownloadListOfProcAfiResult) {
                for (ProcAfi procAfi : procAfiList) {
                    String name = images + "/" + procAfi.getImagen();
                    TextSliderView textSliderView = new TextSliderView(MenuActivity.this);
                    textSliderView
                            .image(new File(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    mDemoSlider.addSlider(textSliderView);
                }
            } else if (result instanceof DownloadListOfReqAfiResult) {
                for (ReqAfi reqAfi : reqAfiList) {
                    String name = images + "/" + reqAfi.getImagen();
                    TextSliderView textSliderView = new TextSliderView(MenuActivity.this);
                    textSliderView
                            .image(new File(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    mDemoSlider.addSlider(textSliderView);
                }
            } else if (result instanceof DownloadListOfProcAteResult) {
                for (ProcAte procAte : procAteList) {
                    String name = images + "/" + procAte.getImagen();
                    TextSliderView textSliderView = new TextSliderView(MenuActivity.this);
                    textSliderView
                            .image(new File(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    mDemoSlider.addSlider(textSliderView);
                }
            } else {
                for (ReqAte reqAte : reqAteList) {
                    String name = images + "/" + reqAte.getImagen();
                    TextSliderView textSliderView = new TextSliderView(MenuActivity.this);
                    textSliderView
                            .image(new File(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    mDemoSlider.addSlider(textSliderView);
                }
            }

            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(MenuActivity.this);

            createMenu();
        }
    }

    private void showMessage(String text, int length){
        Toast.makeText(this, text, length).show();
    }


    public void createMenu() {
        try {
            lstMenu = menuController.getMenuList(2);
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

    private void openScreen(int i) {
        Intent intent = new Intent(MenuActivity.this, WizardPRActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("pages", images.size());
        intent.putExtra("tipafi_id", getIntent().getStringExtra("tipafi_id"));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}