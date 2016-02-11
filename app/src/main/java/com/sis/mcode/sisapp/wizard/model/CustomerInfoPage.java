package com.sis.mcode.sisapp.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.sis.mcode.sisapp.ConsultaActivity;
import com.sis.mcode.sisapp.wizard.ui.CustomerInfoFragment;

import java.util.ArrayList;

public class CustomerInfoPage extends Page {

    public static final String APATERNO_DATA_KEY = "apaterno";
    public static final String AMATERNO_DATA_KEY = "amaterno";
    public static final String PNOMBRE_DATA_KEY = "pnombre";
    public static final String SNOMBRE_DATA_KEY = "snombre";

    public CustomerInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return CustomerInfoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Apellido paterno", mData.getString(APATERNO_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Apellido materno", mData.getString(AMATERNO_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Primer nombre", mData.getString(PNOMBRE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Segundo nombre", mData.getString(SNOMBRE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(APATERNO_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(AMATERNO_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(PNOMBRE_DATA_KEY));
    }

}