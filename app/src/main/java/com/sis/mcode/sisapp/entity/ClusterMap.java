package com.sis.mcode.sisapp.entity;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by manuelmoyano on 2/25/16.
 */


public class ClusterMap implements ClusterItem {
    private final String title;
    private final String snippet;
    private final BitmapDescriptor icon;
    private final LatLng mPosition;

    public ClusterMap(String title, String snippet, BitmapDescriptor icon, LatLng position) {
        this.title = title;
        this.snippet = snippet;
        this.icon = icon;
        this.mPosition = position;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public BitmapDescriptor getIcon() {
        return icon;
    }
}
