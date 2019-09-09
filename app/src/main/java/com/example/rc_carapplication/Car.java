package com.example.rc_carapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Car {

    static double frequency = 2;
    static double dead_frequency = 3;
    static double burst_us = 4;
    static double spacing_us = 5;

    static String carIpAddress = "localhost";
    static int carPort = 12345;

    // repeats codes
    static int startRepeats;
    static int stopRepeats;

    static int forwardRepeats;
    static int backwardsRepeats;
    static int forwardRightRepeats;
    static int forwardLeftRepeats;
    static int backwardsRightRepeats;
    static int backwardsLeftRepeats;
    static int rightRepeats = 1;
    static int leftRepeats;

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
        sendStartMsg(12);

    }

    // bring the car to a halt.
    public void stop() {
        JSONArray command = new JSONArray();
        command.put(createCommand(stopRepeats));
        sendMessage(command);
    }

    private void sendStartMsg(int repeats)  {
        JSONArray command = new JSONArray();
        command.put(createCommand(startRepeats));
        command.put(createCommand(repeats));
        sendMessage(command);
    }

    private void sendMessage(JSONArray command)  {
        String cmdString = command.toString();
        byte[] byteOut = cmdString.getBytes();

        // Don't care 'bout that error
        try {
            OutputStream outputstream = socket.getOutputStream();
            outputstream.write(byteOut);
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    // create JSONObject
    private JSONObject createCommand(double repeats) {
        JSONObject command = new JSONObject();
        try {
            // add static things
            command.put("frequency", frequency);
            command.put("dead_frequency", dead_frequency);
            command.put("burst_us", burst_us);
            command.put("spacing_us", spacing_us);
            // direction
            command.put("repeats", repeats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }

}
