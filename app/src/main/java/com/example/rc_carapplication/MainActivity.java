package com.example.rc_carapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    static Car car;
    static final String ip = "192.168.8.99";
    static final int cameraPort = 5004;
    static final int carPort = 12345;
    GPSHandler gpsdH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView gpsdText = (TextView) findViewById(R.id.gpsdInformation);
        gpsdH = new GPSHandler(gpsdText, ip);

        // buttons for moving
        ImageButton down = (ImageButton) findViewById(R.id.downBtn);
        ImageButton up = (ImageButton) findViewById(R.id.upBtn);
        ImageButton left = (ImageButton) findViewById(R.id.leftBtn);
        ImageButton right = (ImageButton) findViewById(R.id.rightBtn);
        ImageButton upRight = (ImageButton) findViewById(R.id.upRightBtn);
        ImageButton upLeft = (ImageButton) findViewById(R.id.upLeftBtn);
        ImageButton downRight = (ImageButton) findViewById(R.id.downRightBtn);
        ImageButton downLeft = (ImageButton) findViewById(R.id.downLeftBtn);

        // button for moving the image
        Button changeImg = (Button) findViewById(R.id.screenshot);

        // buttin for getting the Panorama
        Button pan = (Button) findViewById(R.id.panoram3a);

        // Place where the Video is displayed
        final WebView webView = (WebView) findViewById(R.id.ourWeb);
        //int contentHight ? webView.getContentHeight()
        WebSettings webSett = webView.getSettings();
        webSett.setJavaScriptEnabled(true);
        webSett.setLoadWithOverviewMode(true);
        webSett.setUseWideViewPort(true);
        //webView.setInitialScale(0);

        webView.loadUrl("http://" + ip);

        // adding a Car
        car = new Car(ip, carPort, cameraPort);
        final CarController controller = new CarController(car);

        // when button is pressed -> get Image from car.
        changeImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                if(eventAction == MotionEvent.ACTION_DOWN){
                    //get new Picture when button is pressed
                    Log.w("test","getNewPicture");
                    car.getCameraPicture();
                }
                return false;
            }
        });

        // when button is pressed -> get panorama from car.
        pan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    controller.panoramaTurn();
                }
                return false;
            }
        });

        // when buttons to move clicked -> drive
        down.setOnTouchListener(createMoveTouchListener(MoveCarEnum.BACKWARDS));

        up.setOnTouchListener(createMoveTouchListener(MoveCarEnum.FORWARD));

        left.setOnTouchListener(createMoveTouchListener(MoveCarEnum.LEFT));

        right.setOnTouchListener(createMoveTouchListener(MoveCarEnum.RIGHT));

        upRight.setOnTouchListener(createMoveTouchListener(MoveCarEnum.FORWARDRIGHT));

        upLeft.setOnTouchListener(createMoveTouchListener(MoveCarEnum.FORWARDLEFT));

        downRight.setOnTouchListener(createMoveTouchListener(MoveCarEnum.BACKWARDSRIGHT));

        downLeft.setOnTouchListener(createMoveTouchListener(MoveCarEnum.BACKWARDSLEFT));


    }

    private View.OnTouchListener createMoveTouchListener(final MoveCarEnum carDirection) {
        View.OnTouchListener touchi = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                Log.w("test","msg" + eventAction);

                if(eventAction == MotionEvent.ACTION_DOWN){
                    //do something when button is pressed
                    car.sendStartMsg(carDirection);
                } else if(eventAction == MotionEvent.ACTION_UP){
                    //do something when button is released
                    car.stop();
                }
                return false;
            }
        };
        return touchi;
    }
    // really ugly to do that here but whatever
    // Volume up / down key make car go forward / backwards
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
