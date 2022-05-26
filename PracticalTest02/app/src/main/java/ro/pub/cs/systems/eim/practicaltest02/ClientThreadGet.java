package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThreadGet extends Thread {
    int port;
    String address;
    String key;
    TextView responseTextView;

    public ClientThreadGet(int port, String address, String key, TextView responseTextView) {
        this.port = port;
        this.address = address;
        this.key = key;
        this.responseTextView = responseTextView;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(address, port);

            BufferedReader bufferedReader = ro.pub.cs.systems.eim.practicaltest02.Utils.getReader(socket);
            PrintWriter printWriter = ro.pub.cs.systems.eim.practicaltest02.Utils.getWriter(socket);

            printWriter.println("get," + key);

            String response = bufferedReader.readLine();
            Log.d("MyApp", response);

//            responseTextView.setText(response);

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
