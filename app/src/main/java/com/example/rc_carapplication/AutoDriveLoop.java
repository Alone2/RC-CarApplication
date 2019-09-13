package com.example.rc_carapplication;

import android.util.Log;

public class AutoDriveLoop implements Runnable{

        PhoneGPSListener gps;
        GPSHandler gpsH;
        CompassListener com;

        double[][] latlonArr;
        static Car car;

        final static int sleepingMillis = 200;
        final static double stopDifference = 0.00001;
        final static double toleranceDegrees = 10;


        public AutoDriveLoop(GPSHandler ge, double[][] latlon, Car car, CompassListener com) {
            this.gpsH = ge;
            //this.gps = ge;
            this.car = car;
            this.latlonArr = latlon;
            this.com = com;

        }

        //  "bug": the car can circle around the target without getting there.
        public void run() {
            for (double[] latlon : latlonArr) {
                // get endpoint lat / lon
                double lat = latlon[0];
                double lon = latlon[1];

                double gpsLon = gpsH.gpsd_Longitude;
                double gpsLat = gpsH.gpsd_Latitude;
                // or gps from phone
                //double gpsLon = gps.getLatitude();
                //double gpsLat = gps.getLatitude()

                // difference between the position of car and the position it's going to.
                double diffLat = gpsLat - lat;
                double diffLon = gpsLon - lon;


                //stop if close enough
                while (Math.abs(diffLat) > stopDifference || Math.abs(diffLon) > stopDifference) {
                    // get gps data
                    double angleWereHeadingTo = gpsH.gpsd_Course;
                    diffLat = gpsH.gpsd_Latitude - lat;
                    diffLon = gpsH.gpsd_Longitude - lon;
                    // phone:
                    // double angleWereHeadingTo = com.getRotation();
                    //diffLat = gps.getLatitude() - lat;
                    //diffLon = gps.getLogitude() - lon;

                    // Angle from north, clockwise
                    double angle = 0;
                    double calculatedAngle = Math.toDegrees(Math.atan(diffLon / diffLat));
                    if (diffLat > 0 && diffLon < 0) {
                        angle = 90 + calculatedAngle;
                    } else if (diffLat < 0 && diffLon < 0) {
                        angle = 270 - calculatedAngle;
                    } else if (diffLat < 0 && diffLon > 0) {
                        angle = 270 + calculatedAngle;
                    } else {
                        angle = 90 - calculatedAngle;
                    }

                    // Debug stuff
                    /*Log.i("latitude", gps.getLatitude() + " " + gps.getLogitude() );
                    Log.i("stuff", String.valueOf(calculatedAngle));
                    Log.i("diffLat", diffLat + " " + diffLon );*/
                    Log.i("carMoves", angle + " " + angleWereHeadingTo );

                    //Log.i("carMoves",String.valueOf(angleWereHeadingTo));

                    // decide where to go
                    if (angle >  angleWereHeadingTo + toleranceDegrees) {
                        Log.i("carMoves","right");
                        car.forwardRight();
                    } else if (angle <  angleWereHeadingTo - toleranceDegrees) {
                        Log.i("carMoves","left");
                        car.forwardLeft();
                    } else {
                        Log.i("carMoves","forward");
                        car.forward();
                    }

                    // sleep for 0.3 sec
                    try {
                        Thread.sleep(sleepingMillis);
                    } catch (InterruptedException v) {
                        System.out.println(v);
                    }
                }
                // stop car when finished
                Log.i("carMoves","stopped");
                car.stop();
            }
        }
}
