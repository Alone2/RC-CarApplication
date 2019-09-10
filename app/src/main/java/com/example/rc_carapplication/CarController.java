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
        for(int i = 0; i<6; i++){
        moveCar(MoveCarEnum.FORWARDRIGHT,0 + i*3000);
        moveCar(MoveCarEnum.BACKWARDSLEFT,500 + i*3000);
        moveCar(MoveCarEnum.SYNC,1300 + i*3000);
        moveCar(MoveCarEnum.FORWARDRIGHT,1800 + i*3000);
        moveCar(MoveCarEnum.SYNC,2400 + i*3000);
        }
    }

}
