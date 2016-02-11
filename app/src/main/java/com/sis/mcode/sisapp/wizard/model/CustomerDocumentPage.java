package com.sis.mcode.sisapp.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.sis.mcode.sisapp.ConsultaActivity;
import com.sis.mcode.sisapp.wizard.ui.CustomerDocumentFragment;

import java.util.ArrayList;

public class CustomerDocumentPage extends Page {

    public static final String NRODOCUMENTO_DATA_KEY = "nrodocumento";

    public CustomerDocumentPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return CustomerDocumentFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Número de documento", mData.getString(NRODOCUMENTO_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NRODOCUMENTO_DATA_KEY));
    }

}