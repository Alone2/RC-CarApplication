package com.example.rc_carapplication;

public class AutoDrive {
    GPSHandler gps;
    Car car;

    public AutoDrive(GPSHandler ge, Car car) {
        this.gps = ge;
        this.car = car;
        // position where to go.
        final double[][] positions = {
                {46.0110946,8.957196}
        };
        // new runnable thread
        Runnable r = new AutoDriveLoop(ge, positions, car);
        new Thread(r).start();

    }

}
