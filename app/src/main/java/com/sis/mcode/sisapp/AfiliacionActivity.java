package com.sis.mcode.sisapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sis.mcode.sisapp.util.RoboActionBarActivity;

public class AfiliacionActivity extends RoboActionBarActivity {


    String tipafidesc;
    String tipafid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afiliacion);


        tipafidesc = getIntent().getStringExtra("tipafi_desc");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_back);
        myToolbar.setTitle(tipafidesc);
        myToolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
