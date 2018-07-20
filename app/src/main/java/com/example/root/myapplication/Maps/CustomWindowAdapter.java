package com.example.root.myapplication.Maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.root.myapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Last Ahmed on 7/10/2018.
 */

public class CustomWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private final View mWindow;
    Context mContext;


    public CustomWindowAdapter(Context mContext) {
        this.mContext = mContext;
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window,null);
    }

    private void renderWindowText(Marker marker , View view){

        String title = marker.getTitle();
        TextView txtTitle = (TextView) view.findViewById(R.id.title);

        if (!title.equals("")){
            txtTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView txtSnippet = (TextView) view.findViewById(R.id.snippet);

        if (!snippet.equals("")){
            txtSnippet.setText(snippet);
        }


    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker,mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker,mWindow);
        return mWindow;
    }
}
