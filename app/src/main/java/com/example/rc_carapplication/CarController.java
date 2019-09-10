package com.example.rc_carapplication;

import android.util.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class CarController {
    private Car car;
    public CarController(Car car){
        this.car = car;
    }
    public void moveCar(final MoveCarEnum direction, int time){
        Timer t = new Timer();
        TimerTask moveObj = new TimerTask() {
            @Override
            public void run() {
                car.sendStartMsg(direction);
            }
        };
        t.schedule(moveObj,time);


    }

    public void panoramaTurn(){

        moveCar(MoveCarEnum.FORWARD,0);
        moveCar(MoveCarEnum.BACKWARDS,600);
        moveCar(MoveCarEnum.SYNC,1200);
        moveCar(MoveCarEnum.FORWARD,1800);
        moveCar(MoveCarEnum.SYNC,2400);



    }

}
