package com.example.root.myapplication.Maps.models;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Last Ahmed on 7/9/2018.
 */

public class PlaceInfo {

    private String name;
    private String address;
    private String placeId;
    private String phone;
    private Uri webSiteUri;
    private float rate;
    private LatLng latLng;
    private String attributions;


    public PlaceInfo(String name, String address, String placeId, String phone, Uri webSiteUri, float rate, LatLng latLng, String attributions) {
        this.name = name;
        this.address = address;
        this.placeId = placeId;
        this.phone = phone;
        this.webSiteUri = webSiteUri;
        this.rate = rate;
        this.latLng = latLng;
        this.attributions = attributions;
    }


    public PlaceInfo(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getWebSiteUri() {
        return webSiteUri;
    }

    public void setWebSiteUri(Uri webSiteUri) {
        this.webSiteUri = webSiteUri;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAttributions() {
        return attributions;
    }

    public void setAttributions(String attributions) {
        this.attributions = attributions;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", placeId='" + placeId + '\'' +
                ", phone='" + phone + '\'' +
                ", webSiteUri=" + webSiteUri +
                ", rate=" + rate +
                ", latLng=" + latLng +
                ", attributions='" + attributions + '\'' +
                '}';
    }
}
