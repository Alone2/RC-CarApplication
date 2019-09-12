# RC-CarApplication
This project was developed during a study-week.
The goal was to develop an Android-Application to control a Raspberry Pi to generate a radio Signal that controls an old toy car.

Once started, the app tries to establish a TCP connection to the Raspberry Pi.

It is required to run and setup this repository on the Raspberry Pi https://github.com/bskari/pi-rc

Currently the IP is set to `192.168.8.99` it currently needs to be changed in the code to match your situation.

In our implementation, the Raspberry Pi generates it's own Lighttpd server to upload images taken by the Car in order to display them in the application.


