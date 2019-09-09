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

        ImageView down = (ImageView) findViewById(R.id.downBtn);

        try {
            car = new Car();
        } catch (Exception e) {
            e.printStackTrace();
        }

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.backwards();

                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.forward();
                }
                return false;
            }
        });




    }

}
