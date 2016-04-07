package com.sis.mcode.sisapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;
import com.google.maps.android.clustering.ClusterManager;
import com.sis.mcode.sisapp.communication.DownloadListOfEessResult;
import com.sis.mcode.sisapp.controller.EessController;
import com.sis.mcode.sisapp.entity.ClusterMap;
import com.sis.mcode.sisapp.entity.Eess;
import com.sis.mcode.sisapp.entity.OwnIconRended;
import com.sis.mcode.sisapp.util.RoboActionBarActivity;

import java.util.List;

import roboguice.activity.RoboFragmentActivity;

public class MapsActivity extends RoboActionBarActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private double lat = -9.445419179530596;
    private double lon = -75.11267010122538;
    private float zoom = Float.parseFloat("5.1465807");

    @Inject
    protected EessController eessController;
    Context mContext;

    private ClusterManager<ClusterMap> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_back);
        myToolbar.setLogo(R.mipmap.ic_launcher);
        myToolbar.setTitle(" | BUSCAR");
        myToolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        showMessageEmg();
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);

        /*if (bestProvider != null){
            Location location = locationManager.getLastKnownLocation(bestProvider);
            lat = location.getLatitude();
            lon = location.getLongitude();
        }*/
        setUpClusterer();


        /*showMessageEmg();

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                lat = cameraPosition.target.latitude;
                lon = cameraPosition.target.longitude;
                getEess();
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);
        }


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        LatLng lalo;
        CameraUpdate zoomUp;
        if (bestProvider != null){
            Location location = locationManager.getLastKnownLocation(bestProvider);
            lat = location.getLatitude();
            lon = location.getLongitude();
            lalo = new LatLng(lat, lon);
            zoomUp = CameraUpdateFactory.zoomTo(16);
        }else{
            lalo = new LatLng(lat,lon);
            zoomUp = CameraUpdateFactory.zoomTo(zoom);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lalo));
        mMap.animateCamera(zoomUp);*/
    }

    private void showMessage(String text, int length){
        Toast.makeText(this, text, length).show();
    }

    private void showMessageEmg(){
        DialogFragment dg = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                return new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.msg_emg)
                        .setNegativeButton(R.string.msg_map, null)
                        .setPositiveButton(R.string.msg_consultar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                goToConsular();
                            }
                        })
                        .create();
            }
        };
        dg.show(getFragmentManager(), "place_order_dialog");
    }

    private void goToConsular(){
        Intent intent = new Intent(this,ConsultaActivity.class);
        this.startActivity(intent);
    }


    private void putEess(double lat, double lon, String name, String address) {
        LatLng ll = new LatLng(lat, lon);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(ll)
                .title(name)
                .snippet(address)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));
    }
    private void getEess(){
        try {
            new downloadEESSTask().execute();
        } catch (Exception e) {
           showMessage(e.getMessage(),Toast.LENGTH_LONG);
        }

    }

    public class downloadEESSTask extends AsyncTask<Void, Void, DownloadListOfEessResult> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected DownloadListOfEessResult doInBackground(Void... params) {
            LatLng ll = new LatLng(lat, lon);
            DownloadListOfEessResult result = new DownloadListOfEessResult();
            result = eessController.downloadEess(ll);
            return result;
        }
        @Override
        protected void onPostExecute(DownloadListOfEessResult result) {
            if (result.isSuccess()){
                //putAllEess(result);
                addItems(result);

            }
        }
    }

    private void putAllEess(DownloadListOfEessResult result){
        for(Eess eess : result.getData()){
            putEess(eess.getLatitude(),eess.getLongitud(), eess.getDescripcion(),eess.getDireccion());
        }
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
                showMessageEmg();
                return true;
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpClusterer() {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), zoom));

        mClusterManager = new ClusterManager<ClusterMap>(this, mMap);

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mClusterManager.onCameraChange(cameraPosition);
                float[] results = new float[1];
                Location.distanceBetween(lat,lon,cameraPosition.target.latitude,cameraPosition.target.longitude,results);
                float km = results[0]/1000;
                if (km > 5) {
                    lat = cameraPosition.target.latitude;
                    lon = cameraPosition.target.longitude;
                    getEess();
                }
            }
        });


        mMap.setOnMarkerClickListener(mClusterManager);
    }

    private void addItems(DownloadListOfEessResult result) {

        /* Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;*/

        // Add ten cluster items in close proximity, for purposes of this example.
        mClusterManager.clearItems();
        for(Eess eess : result.getData()){
            ClusterMap offsetItem = new ClusterMap(eess.getDescripcion(),
                                                   eess.getDireccion(),
                                                   BitmapDescriptorFactory.fromResource(R.drawable.hospital),
                                                   new LatLng(eess.getLatitude(),eess.getLongitud()));
            mClusterManager.setRenderer(new OwnIconRended(getApplicationContext(),mMap,mClusterManager));
            mClusterManager.addItem(offsetItem);
        }

        /*for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            ClusterMap offsetItem = new ClusterMap(lat, lng);
            mClusterManager.addItem(offsetItem);
        }*/
    }



}
