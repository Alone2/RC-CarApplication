package com.example.rc_carapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    static Car car;
    static final String ip = "192.168.8.104";
    static final int cameraPort = 1900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton down = (ImageButton) findViewById(R.id.downBtn);
        ImageButton up = (ImageButton) findViewById(R.id.upBtn);
        ImageButton left = (ImageButton) findViewById(R.id.leftBtn);
        ImageButton right = (ImageButton) findViewById(R.id.rightBtn);
        ImageButton upRight = (ImageButton) findViewById(R.id.upRightBtn);
        ImageButton upLeft = (ImageButton) findViewById(R.id.upLeftBtn);
        ImageButton downRight = (ImageButton) findViewById(R.id.downRightBtn);
        ImageButton downLeft = (ImageButton) findViewById(R.id.downLeftBtn);

        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        // adding camera stream to view
        Uri cameraUri = Uri.parse(("rtsp://" + ip + ":" + cameraPort + "/"));
        videoView.setVideoURI(cameraUri);
        videoView.start();
        // adding a Car
        car = new Car(ip);
        final CarController controller = new CarController(car);

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    //car.backwards();
                    controller.panoramaTurn();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    //car.stop();
                }
                return false;
            }
        });

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.forward();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        });


        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                Log.w("test","msg" + eventAction);

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.left();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                Log.w("test","msg" + eventAction);

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.right();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        });

        upRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                Log.w("test","msg" + eventAction);

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.forwardRight();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        });

        upLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                Log.w("test","msg" + eventAction);

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.forwardLeft();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        });

        downRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                Log.w("test","msg" + eventAction);

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.backwardsRight();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        });

        downLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                Log.w("test","msg" + eventAction);

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.backwardsLeft();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        });



    }
    // really ugly to do that here but whatever
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            car.backwards();
        }
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            car.forward();
        }
        return true;
    }
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            car.stop();
        }
        return true;
    }

}
