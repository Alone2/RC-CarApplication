package com.example.rc_carapplication;

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
        TimerTask timerTaskObj = new TimerTask() {
            @Override
            public void run() {
                car.stop();
            }
        };
        t.schedule(timerTaskObj,0,15000);
    }

}
