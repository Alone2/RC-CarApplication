package com.example.rc_carapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;

import java.io.IOException;
import java.net.Socket;

public class Car  {

    static double frequency = 27.145;
    static double dead_frequency = 49.83;
    static double spacing_us = 400;

    static String carIpAddress;
    static String cameraIpAddress;
    static int carPort;
    static int cameraPort;

    Socket socket;
    Socket cameraSocket;
    SocketHandler sockethandler;


    public Car(String ipAddress, int carPort, int cameraPort) {
        this.carIpAddress = ipAddress;
        this.carPort = carPort;
        this.cameraIpAddress = ipAddress;
        this.cameraPort = cameraPort;
        sockethandler = new SocketHandler();
    }

    // drive with MoveCarEnum (you can cycle through 'MoveCarEnum')
    public void drive(MoveCarEnum move) {
        JSONArray command = new JSONArray();
        command.put(createCommand(move.getValue(), move.getBurst()));

        SocketHandler so = new SocketHandler();
        so.execute(command);
    }

    public void storePanoramaPicture() {
        CameraSocketHandler so = new CameraSocketHandler();
        so.execute("storePanoramaPicture");
    }

    public void stopPanorama() {
        CameraSocketHandler so = new CameraSocketHandler();
        so.execute("stopPanorama");
    }

    public void getCameraPicture() {
        CameraSocketHandler so = new CameraSocketHandler();
        so.execute("getPicture");
    }

    public void forward() {
        sendStartMsg(MoveCarEnum.FORWARD);
    }

    public void backwards() {
        sendStartMsg(MoveCarEnum.BACKWARDS);
    }


    public void forwardRight() {
        sendStartMsg(MoveCarEnum.FORWARDRIGHT);
    }


    public void forwardLeft() {
        sendStartMsg(MoveCarEnum.FORWARDLEFT);
    }


    public void backwardsRight() {
        sendStartMsg(MoveCarEnum.BACKWARDSRIGHT);
    }


    public void backwardsLeft() {
        sendStartMsg(MoveCarEnum.BACKWARDSLEFT);
    }


    public void left() {
        sendStartMsg(MoveCarEnum.LEFT);
    }

    public void right() {
        sendStartMsg(MoveCarEnum.RIGHT);

    }

    // bring the car to a halt.
    public void stop() {
        Log.w("test","stopTask");

        drive(MoveCarEnum.SYNC);
    }

    public void sendStartMsg(MoveCarEnum e)  {
        JSONArray command = new JSONArray();
        command.put(createCommand(MoveCarEnum.SYNC.getValue(), MoveCarEnum.SYNC.getBurst()));
        command.put(createCommand(e.getValue(), e.getBurst()));

        SocketHandler so = new SocketHandler();
        so.execute(command);
    }

    // create JSONObject
    private JSONObject createCommand(double repeats, double burst_us) {
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

    // SocketHandler because socket can not be handled in main thread...
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

    // SocketHandler for Camera...
    // execute -> 1 arguments -> String command
    class CameraSocketHandler extends AsyncTask<String, Void, Integer>  {
        public Integer doInBackground(String... strCmd) {
            try {
                if (cameraSocket == null)
                    cameraSocket = new Socket(cameraIpAddress, cameraPort);

            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] byteOut = strCmd[0].getBytes();

            try {
                DataOutputStream outToServer = new DataOutputStream(cameraSocket.getOutputStream());
                outToServer.write(byteOut);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return 1;
        }


    }

}
