package com.sis.mcode.sisapp.entity;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by manuelmoyano on 2/25/16.
 */
public class OwnIconRended extends DefaultClusterRenderer<ClusterMap>{

    public OwnIconRended(Context context, GoogleMap map,
                           ClusterManager<ClusterMap> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(ClusterMap item, MarkerOptions markerOptions) {
        markerOptions.icon(item.getIcon());
        markerOptions.snippet("Dirección: " + item.getSnippet());
        markerOptions.title("EESS: " + item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

}
