package com.example.rc_carapplication;

import android.util.Log;

import de.taimos.gpsd4java.api.ObjectListener;
import de.taimos.gpsd4java.backend.GPSdEndpoint;
import de.taimos.gpsd4java.types.TPVObject;

public class GPSHandler {
    GPSdEndpoint gspdE;
    String gpsServer = "192.168.8.104";
    int gspPort = 2948;
    double gpsd_Latitude;
    double gpsd_Longitude;
    double gpsd_Speed;
    double gpsd_Course;

    public GPSHandler(){
        gspdE = new GPSdEndpoint(gpsServer, gspPort);
        Log.w("test", "WORKING");
        gspdE.addListener(new ObjectListener(){
            @Override
            public void handleTPV(TPVObject tpv) {
                gpsd_Latitude = tpv.getLatitude();
                gpsd_Longitude = tpv.getLongitude();
                gpsd_Speed = tpv.getSpeed();
                gpsd_Course = tpv.getCourse();
                Log.w("test", "GPS works " + gpsd_Speed);
            }
        });
        try {
            gspdE.start();
            gspdE.watch(true,true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
