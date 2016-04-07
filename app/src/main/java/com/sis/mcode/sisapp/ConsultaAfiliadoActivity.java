package com.sis.mcode.sisapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.inject.Inject;
import com.sis.mcode.sisapp.communication.DownloadListOfInfoResult;
import com.sis.mcode.sisapp.controller.AfiliadoController;
import com.sis.mcode.sisapp.entity.Afiliado;
import com.sis.mcode.sisapp.util.RoboActionBarActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsultaAfiliadoActivity extends RoboActionBarActivity {

    @Inject
    private AfiliadoController afiliadoController;

    DownloadListOfInfoResult infoResult = new DownloadListOfInfoResult();

    private ProgressDialog dialog;

    public static String TIPO_CONSULTA;

    public static String APELLIDO_PATERNO;
    public static String APELLIDO_MATERNO;
    public static String PRIMER_NOMBRE;
    public static String SEGUNDO_NOMBRE;

    public static int TIPO_DOCUMENTO;
    public static String NRO_DOCUMENTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_afiliado);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setTitle(" | COINCIDENCIAS");
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);

        showResult();

    }

    public void showResult(){
        new DownloadInformationTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ConsultaAfiliadoActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class DownloadInformationTask extends AsyncTask<Void, Void, DownloadListOfInfoResult> {

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setTitle("Consultando");
            dialog.setMessage(getResources().getString(R.string.msg_please_wait));
            dialog.show();
        }

        @Override
        protected DownloadListOfInfoResult doInBackground(
                Void... params) {
            if (TIPO_CONSULTA.equals("Datos Personales")) {
                infoResult = afiliadoController.getInfoByName(
                        APELLIDO_PATERNO, APELLIDO_MATERNO,
                        PRIMER_NOMBRE, SEGUNDO_NOMBRE);
            } else {
                infoResult = afiliadoController.getInfoByDocNumber(
                        TIPO_DOCUMENTO, NRO_DOCUMENTO
                );
            }
            return infoResult;
        }

        @Override
        protected void onPostExecute(final DownloadListOfInfoResult result) {
            dialog.dismiss();

            if(result.isSuccess()) {
                if (result.getInfo().size() <= 0){
                    showMessageNoInfo();
                } else {
                    final ArrayList<String> list = new ArrayList<>();
                    for (Afiliado afiliado : result.getInfo()) {
                        list.add(afiliado.toString());
                    }

                    ListView listView = (ListView) findViewById(R.id.listview_info);
                    StableArrayAdapter adapter = new StableArrayAdapter(ConsultaAfiliadoActivity.this, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            Intent intent = new Intent(ConsultaAfiliadoActivity.this, DetalleAfiliadoActivity.class);
                            intent.putExtra("tabla", result.getInfo().get(position).getTabla());
                            intent.putExtra("numReg", result.getInfo().get(position).getNumReg());
                            startActivity(intent);
                        }

                    });
                }
            }
        }
    }

    private void showMessageNoInfo(){
        DialogFragment dg = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                return new AlertDialog.Builder(getActivity())
                        .setMessage("No se encontraron coincidencias...")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .create();
            }
        };
        dg.show(getFragmentManager(), "place_order_dialog");
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

}