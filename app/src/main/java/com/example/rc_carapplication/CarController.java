package com.example.rc_carapplication;

import java.util.Timer;
import java.util.TimerTask;

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
    public void takeAPicture(int time, final boolean stopPanorama){
        Timer t = new Timer();
        TimerTask moveObj = new TimerTask() {
            @Override
            public void run() {
                if(!stopPanorama){
                    car.storePanoramaPicture();
                }else{
                    car.stopPanorama();
                }
            }
        };
        t.schedule(moveObj, time);
    }

    public void panoramaTurn(){

        for(int i = 0; i<6; i++){
        moveCar(MoveCarEnum.FORWARDRIGHT,0 + i*4000);
        moveCar(MoveCarEnum.BACKWARDSLEFT,500 + i*4000);
        moveCar(MoveCarEnum.SYNC,1300 + i*4000);
        moveCar(MoveCarEnum.FORWARDRIGHT,1800 + i*4000);
        moveCar(MoveCarEnum.SYNC,2400 + i*4000);
        takeAPicture(3000 + i*4000,false);
        }
        takeAPicture((3100 + 5*4000),true);
    }

}
