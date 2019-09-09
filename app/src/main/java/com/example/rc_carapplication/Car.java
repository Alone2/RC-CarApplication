package com.example.rc_carapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Car {

    static double frequency ;
    static double dead_frequency ;
    static double burst_us ;
    static double spacing_us ;

    static String carIpAddress;
    static int carPort;

    static int startRepeats;
    static int stopRepeats;

    Socket socket;


    public Car() throws UnknownHostException, IOException {
        socket = new Socket(carIpAddress, carPort);
    }

    public void forward() {

    }

    public void backwards() {

    }


    public void forwardRight() {

    }


    public void forwardLeft() {

    }


    public void backwardsRight() {

    }


    public void backwardsLeft() {

    }


    public void left() {

    }


    public void right() {

    }

    public void stop() {

    }

    private void sendMsg(int repeats) throws Exception {
        JSONArray command = new JSONArray();
        command.put(createCommand(startRepeats));
        command.put(createCommand(repeats));

        String cmdString = command.toString();
        byte[] byteOut = cmdString.getBytes();

        OutputStream outputstream = socket.getOutputStream();

        outputstream.write(byteOut);

    }

    // create JSONObject
    private JSONObject createCommand(double repeats) throws Exception  {
        JSONObject command = new JSONObject();
        // add static things
        command.put("frequency", frequency);
        command.put("dead_frequency", dead_frequency);
        command.put("burst_us", burst_us);
        command.put("spacing_us", spacing_us);
        // direction
        command.put("repeats", repeats);
    }

}
