package com.example.rc_carapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    static Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton down = (ImageButton) findViewById(R.id.upLeftBtn);

        try {
            car = new Car();
        } catch (Exception e) {
            e.printStackTrace();
        }

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                Log.w("test","msg" + eventAction);

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.backwards();
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        });





    }

}
