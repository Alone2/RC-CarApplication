package com.example.rc_carapplication;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.Bundle;

public class PhoneGPSListener implements LocationListener {
    double longitude;
    double latitude;
    double bearing;
    Location loc;

    @Override
    public void onLocationChanged(Location loc) {
        this.loc = loc;
        this.longitude = loc.getLongitude();
        this.latitude = loc.getLatitude();
        this.bearing = loc.getBearing();
    }

    public double getBearing() {
        return bearing;
    }

    public double getLogitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}


}
