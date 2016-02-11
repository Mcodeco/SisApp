package com.sis.mcode.sisapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.controller.AfiliadoController;
import com.sis.mcode.sisapp.entity.InfoDetail;
import com.sis.mcode.sisapp.util.RoboActionBarActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetalleAfiliadoActivity extends RoboActionBarActivity {

    @Inject
    private AfiliadoController afiliadoController;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_afiliado);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setTitle("RESULTADO");
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);

        viewDetail();
    }

    private void  viewDetail() {
        new DownloadInformationTask().execute();
    }

    public class DownloadInformationTask extends AsyncTask<Void, Void, InfoDetail> {

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setTitle("Bienvenido");
            dialog.setMessage(getResources().getString(R.string.msg_please_wait));
            dialog.show();
        }

        @Override
        protected InfoDetail doInBackground(
                Void... params) {
            String tabla = getIntent().getStringExtra("tabla");
            int numReg = Integer.parseInt(getIntent().getStringExtra("numReg"));
            InfoDetail infoDetail = afiliadoController.getDetail(tabla, numReg, "");
            return infoDetail;
        }

        @Override
        protected void onPostExecute(InfoDetail result) {
            dialog.dismiss();

            ArrayList<String> list = new ArrayList<>();
            list.add("Apellidos y Nombres: " + result.getApellidoPaterno() + " " + result.getApellidoMaterno() + " " + result.getNombres());
            list.add("Documento de identidad : " + result.getDocumento());
            list.add("N° de afiliación/inscripción : " + result.getNroAfiliacion());
            list.add("Tipo de seguro: " + result.getTipoSeguro());
            list.add("Tipo de asegurado: " + result.getTipoAsegurado());
            list.add("Tipo de formato: " + "AFILIACIÓN");
            list.add("Fecha de afiliación: " + result.getFechaAfiliacion());
            list.add("Plan de beneficiados: " + result.getPlanBeneficio());
            list.add("Establecimiento de salud: " + result.getEstablecimiento());
            list.add("Ubicación de establecimiento de salud: " + result.getEstablecimiento());
            list.add("Estado: " + result.getEstado());
            list.add("Hasta: " + result.getHasta());

            ListView listView = (ListView) findViewById(R.id.listview_detail);
            StableArrayAdapter adapter = new StableArrayAdapter(DetalleAfiliadoActivity.this,
                    android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);

        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}