package com.sis.mcode.sisapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sis.mcode.sisapp.util.InternetStatus;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);
            }
        }, 700);
    }


}
