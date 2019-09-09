package com.example.rc_carapplication;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Car  {

    static double frequency = 27.145;
    static double dead_frequency = 49;
    static double burst_us = 1200;
    static double spacing_us = 400;

    static String carIpAddress = "192.168.151.185";
    static int carPort = 12345;

    // repeats codes
    static int startRepeats = 4;
    static int stopRepeats = 4;

    static int forwardRepeats = 11;
    static int backwardsRepeats = 39;
    static int forwardRightRepeats = 33;
    static int forwardLeftRepeats = 27;
    static int backwardsRightRepeats = 45;
    static int backwardsLeftRepeats = 51;
    static int rightRepeats = 64;
    static int leftRepeats = 59;

    Socket socket;
    SocketHandler sockethandler;


    public Car() throws UnknownHostException,IOException {
        sockethandler = new SocketHandler();
    }

    public void forward() {
        sendStartMsg(forwardRepeats);
    }

    public void backwards() {
        sendStartMsg(backwardsRepeats);
    }


    public void forwardRight() {
        sendStartMsg(forwardRightRepeats);
    }


    public void forwardLeft() {
        sendStartMsg(forwardLeftRepeats);
    }


    public void backwardsRight() {
        sendStartMsg(backwardsRightRepeats);
    }


    public void backwardsLeft() {
        sendStartMsg(backwardsLeftRepeats);
    }


    public void left() {
        sendStartMsg(leftRepeats);
    }

    public void right() {
        sendStartMsg(rightRepeats);

    }

    // bring the car to a halt.
    public void stop() {
        JSONArray command = new JSONArray();
        command.put(createCommand(stopRepeats));

        SocketHandler so = new SocketHandler();
        so.execute(command);

    }

    private void sendStartMsg(int repeats)  {
        JSONArray command = new JSONArray();
        command.put(createCommand(startRepeats));
        command.put(createCommand(repeats));

        SocketHandler so = new SocketHandler();
        so.execute(command);


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

    class SocketHandler extends AsyncTask<JSONArray, Void, Integer>  {


        public Integer doInBackground(JSONArray... ja) {
            try {
                if (socket == null)
                    socket = new Socket(carIpAddress, carPort);

            } catch (IOException e) {
                e.printStackTrace();
            }
            sendMessage(ja[0]);

            return 1;
        }


        private void sendMessage(JSONArray command)  {
            String cmdString = command.toString();
            byte[] byteOut = cmdString.getBytes();

            // Don't care 'bout that error
            try {
                DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

                outToServer.write(byteOut);
            }  catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
