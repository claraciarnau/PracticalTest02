package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThreadPut extends Thread {
    int port;
    String address;
    String key;
    String value;
    TextView responseTextView;

    public ClientThreadPut(int port, String address, String key, String value, TextView responseTextView) {
        this.port = port;
        this.address = address;
        this.key = key;
        this.value = value;
        this.responseTextView = responseTextView;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(address, port);

            BufferedReader bufferedReader = Utils.getReader(socket);
            PrintWriter printWriter = Utils.getWriter(socket);

            printWriter.println("put," + key + "," + value);

            String response = bufferedReader.readLine();
            Log.d("MyApp", response);

            responseTextView.post(new Runnable() {
                @Override
                public void run() {
                    responseTextView.setText(response);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
