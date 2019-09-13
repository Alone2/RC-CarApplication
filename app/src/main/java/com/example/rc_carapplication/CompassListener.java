package com.example.rc_carapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class CompassListener implements SensorEventListener {

    // "inspired" (more or less copied) from:
    // https://stackoverflow.com/questions/26831758/simple-compass-using-magnetometer-android-java
    // and https://www.techrepublic.com/article/pro-tip-create-your-own-magnetic-compass-using-androids-internal-sensors/

    Sensor accel;
    Sensor magnet;
    SensorManager sensMag;
    float[] accelValues = new float[3];
    float[] magnetValues  = new float[3];
    boolean magnetHasValue = false;
    boolean accelHasValue = false;
    double rotation;

    public CompassListener(SensorManager sm) {
        accel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnet = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sm.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(this, magnet, SensorManager.SENSOR_DELAY_GAME);
        sensMag = sm;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accel) {
            System.arraycopy(event.values, 0, accelValues, 0, event.values.length);
            accelHasValue = true;
        } else if (event.sensor == magnet) {
            System.arraycopy(event.values, 0, magnetValues, 0, event.values.length);
            magnetHasValue = true;
        }

        float identityMatrix[] = new float[9];
        float rotationMatrix[] = new float[9];

        //When no values return
        if (!(accelHasValue && magnetHasValue))
            return;

        SensorManager.getRotationMatrix(rotationMatrix, identityMatrix, accelValues, magnetValues);

        float orientationMatrix[] = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationMatrix);
        float rotationInRadians = orientationMatrix[0];
        double firstRotation = Math.toDegrees(rotationInRadians);
        // rotation from -180 to +180 -> 0 - 360
        if (firstRotation < 0) {
            rotation = 360 + firstRotation;
        } else {
            rotation = firstRotation;
        }
        // Debug
        Log.w("rotation:", String.valueOf(firstRotation));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    public double getRotation() {
        return rotation;
    }


}
