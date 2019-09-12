package com.example.rc_carapplication;

import android.util.Log;
import android.widget.TextView;

import de.taimos.gpsd4java.api.ObjectListener;
import de.taimos.gpsd4java.backend.GPSdEndpoint;
import de.taimos.gpsd4java.types.TPVObject;

public class GPSHandler {
    GPSdEndpoint gspdE;
    String gpsServer;
    int gspPort = 2948;
    double gpsd_Latitude;
    double gpsd_Longitude;
    double gpsd_Speed;
    double gpsd_Course;
    TextView gpsdInformation;

    public GPSHandler(TextView tV, String ip){
        gspdE = new GPSdEndpoint(gpsServer, gspPort);
        gpsdInformation = tV;
        gpsServer = ip;

        gspdE.addListener(new ObjectListener(){
            @Override
            public void handleTPV(TPVObject tpv) {
                gpsd_Latitude = tpv.getLatitude();
                gpsd_Longitude = tpv.getLongitude();
                gpsd_Speed = tpv.getSpeed();
                gpsd_Course = tpv.getCourse();
                gpsdInformation.setText("gpsd_Speed: " + gpsd_Speed + "\n" + "gpsd_Course: " + gpsd_Course);

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
