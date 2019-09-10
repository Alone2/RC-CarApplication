package com.example.rc_carapplication;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class CarController {
    private Car car;
    public CarController(Car car){
        this.car = car;
    }
    public void stopCar(){

    }

    public void panoramaTurn(){
        Timer t = new Timer();
        car.forward();
        TimerTask stopObj = new TimerTask() {
            @Override
            public void run() {
                car.stop();
                Log.w("test","stopTask");
            }
        };
        TimerTask backWard = new TimerTask() {
            @Override
            public void run() {
                //car.backwards();
                Log.w("test","backTask");
            }
        };

        t.schedule(backWard,1500, 1500);
        t.schedule(stopObj,0,1500);


    }

}
