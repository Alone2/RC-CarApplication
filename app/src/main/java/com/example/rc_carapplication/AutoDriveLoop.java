package com.example.rc_carapplication;

public class AutoDriveLoop implements Runnable{

        GPSHandler gps;

        double[][] latlonArr;
        static Car car;
        final int sleepingMillis = 200;
        final double stopDifference = 0.0005;


        public AutoDriveLoop(GPSHandler ge, double[][] latlon, Car car) {
            this.gps = ge;
            this.car = car;
            this.latlonArr = latlon;

        }

        //  "bug": the car can circle around the target without getting there.
        public void run() {
            for (double[] latlon : latlonArr) {
                // get endpoint lat / lon
                double lat = latlon[0];
                double lon = latlon[1];

                // difference between the position of car and the position it's going to.
                double diffLat = gps.gpsd_Latitude - lat;
                double diffLon = gps.gpsd_Latitude - lon;


                //stop if close enough
                while (Math.abs(diffLat) > stopDifference && Math.abs(diffLon) > stopDifference) {
                    // get gps data
                    double carCourse = gps.gpsd_Course;
                    diffLat = gps.gpsd_Latitude - lat;
                    diffLon = gps.gpsd_Longitude - lon;

                    // Angla from north, clockwise
                    double plusAngle = 0;
                    if (diffLat > 0 && diffLon < 0) {
                        plusAngle = 90;
                    } else if (diffLat < 0 && diffLon < 0) {
                        plusAngle = 180;
                    } else if (diffLat < 0 && diffLon > 0) {
                        plusAngle = 270;
                    }
                    double angle = Math.atan(diffLon / diffLat) + plusAngle;
                    double angleWereHeadingTo = carCourse;

                    // decide where to go
                    double toleranceDegrees = 5;
                    if (angle - angleWereHeadingTo > toleranceDegrees) {
                        car.forwardLeft();
                    } else if (angle - angleWereHeadingTo < toleranceDegrees) {
                        car.forwardRight();
                    } else {
                        car.forward();
                    }

                    // sleep for 0.3 sec
                    try {
                        Thread.sleep(sleepingMillis);
                    } catch (InterruptedException v) {
                        System.out.println(v);
                    }
                }
                car.stop();
            }
        }
}
